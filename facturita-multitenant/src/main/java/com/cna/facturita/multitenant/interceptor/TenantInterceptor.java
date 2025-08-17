package com.cna.facturita.multitenant.interceptor;

import com.cna.facturita.multitenant.context.TenantContext;
import com.cna.facturita.multitenant.resolver.SubdomainTenantResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor para la gestión multitenant en aplicaciones Spring MVC.
 * <p>
 * Este interceptor se encarga de:
 * <ul>
 * <li>Resolver el tenant actual en cada request HTTP, usando subdominio, header
 * o configuración.</li>
 * <li>Establecer el contexto de tenant global para el ciclo de vida de la
 * petición.</li>
 * <li>Limpiar el contexto al finalizar la petición para evitar fugas entre
 * threads.</li>
 * <li>Agregar un header de respuesta para debugging y trazabilidad.</li>
 * </ul>
 * <p>
 * Buenas prácticas:
 * <ul>
 * <li>El contexto de tenant se maneja con ThreadLocal, por lo que es crítico
 * limpiarlo en afterCompletion.</li>
 * <li>La resolución del tenant debe ser robusta y considerar tanto subdominios
 * como headers (útil en desarrollo y producción).</li>
 * <li>El interceptor debe ser registrado en la configuración WebMvcConfigurer
 * para asegurar ejecución en cada request.</li>
 * <li>Los logs en nivel DEBUG permiten trazabilidad completa en ambientes de
 * desarrollo y troubleshooting.</li>
 * </ul>
 * <p>
 * Ejemplo de uso:
 * 
 * <pre>
 *   registry.addInterceptor(new TenantInterceptor(...)).addPathPatterns("/**");
 * </pre>
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);

    private final SubdomainTenantResolver tenantResolver;

    public TenantInterceptor(SubdomainTenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }

    /**
     * Pre-procesa cada request para resolver y establecer el tenant actual.
     * <p>
     * - Si el host es localhost, intenta extraer el subdominio del header Origin
     * (útil en desarrollo con frontend separado).
     * - Si el host es un subdominio válido, lo usa como identificador de tenant.
     * - Si no se puede resolver, usa el tenant por defecto.
     * <p>
     * El tenant se establece en el contexto global y se agrega un header de
     * respuesta para debugging.
     *
     * @param request  HttpServletRequest actual
     * @param response HttpServletResponse actual
     * @param handler  Handler de Spring MVC
     * @return true para continuar el procesamiento
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {
        String tenant = tenantResolver.resolveTenant(request);

        // Si el serverName es localhost, intentar extraer el subdominio del header
        // Origin
        if ("localhost".equalsIgnoreCase(request.getServerName()) || "127.0.0.1".equals(request.getServerName())) {
            String origin = request.getHeader("Origin");
            if (origin != null) {
                try {
                    java.net.URI uri = new java.net.URI(origin);
                    String host = uri.getHost(); // demo.localhost
                    if (host != null && host.contains(".")) {
                        String subdomain = host.substring(0, host.indexOf("."));
                        if (subdomain.length() > 1) {
                            tenant = subdomain;
                            log.debug("[TenantInterceptor] Subdominio extraído de Origin: {}", subdomain);
                        }
                    }
                } catch (Exception e) {
                    log.debug("[TenantInterceptor] Error extrayendo subdominio de Origin: {}", origin);
                }
            }
        }

        TenantContext.setCurrentTenant(tenant);
        log.debug("[TenantInterceptor] Request from {} -> Tenant: {}", request.getServerName(), tenant);
        response.setHeader("X-Tenant", tenant);
        return true;
    }

    /**
     * Limpia el contexto de tenant al finalizar la petición.
     * <p>
     * Es fundamental para evitar fugas de contexto entre threads en ambientes
     * concurrentes.
     *
     * @param request  HttpServletRequest actual (no nulo)
     * @param response HttpServletResponse actual (no nulo)
     * @param handler  Handler de Spring MVC (no nulo)
     * @param ex       Excepción lanzada durante el procesamiento, puede ser nula
     */
    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) {
        // Limpiar el contexto después del request
        log.debug("[TenantInterceptor] Limpiando contexto de tenant al finalizar la petición. Valor actual: {}",
                TenantContext.getCurrentTenant());
        TenantContext.clear();
        log.debug("[TenantInterceptor] Contexto de tenant limpiado.");
    }
}
