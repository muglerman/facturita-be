package com.cna.facturita.multitenant.resolver;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Resuelve el tenant basado en el subdominio de la URL
 */
@Component
public class SubdomainTenantResolver {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SubdomainTenantResolver.class);
    
    private static final String DEFAULT_TENANT = "cna";
    private static final String PRODUCTION_DOMAIN = "facturitapro.com";
    private static final String DEV_DOMAIN = "localhost";
    
    // Patrón para validar nombres de tenant (solo letras, números y guiones)
    private static final Pattern TENANT_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");
    
    /**
     * Resuelve el tenant desde la URL del request
     * 
     * Ejemplos:
     * - localhost:8080 -> cna (desarrollo)
     * - facturita.com -> cna (producción)
     * - cna.facturita.com -> cna
     * - empresa1.facturita.com -> empresa1
     * - localhost -> cna (desarrollo sin puerto)
     */
    public String resolveTenant(HttpServletRequest request) {
        String serverName = request.getServerName();
        String headerxTenant = request.getHeader("x-tenant");
        log.debug("[SubdomainTenantResolver] serverName: {}", serverName);
        log.debug("[SubdomainTenantResolver] x-tenant header: {}", headerxTenant);

        if (headerxTenant != null){
            log.debug("[SubdomainTenantResolver] Usando x-tenant header: {}", headerxTenant);
            return headerxTenant;
        }

        if (serverName == null) {
            log.debug("[SubdomainTenantResolver] serverName es null, usando DEFAULT_TENANT: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT;
        }

        // Desarrollo: localhost o localhost:puerto
        if (serverName.equals(DEV_DOMAIN) || serverName.equals("127.0.0.1")) {
            log.debug("[SubdomainTenantResolver] serverName es localhost o 127.0.0.1, usando DEFAULT_TENANT: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT;
        }

        // Producción: dominio principal
        if (serverName.equals(PRODUCTION_DOMAIN)) {
            log.debug("[SubdomainTenantResolver] serverName es dominio producción, usando DEFAULT_TENANT: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT;
        }

        // Subdominios
        String tenant = extractSubdomain(serverName);
        log.debug("[SubdomainTenantResolver] subdominio extraído: {}", tenant);

        // Validar que el tenant sea válido
        if (isValidTenant(tenant)) {
            log.debug("[SubdomainTenantResolver] tenant válido: {}", tenant);
            return tenant;
        }

        // Si no es válido, usar default
        log.debug("[SubdomainTenantResolver] tenant no válido, usando DEFAULT_TENANT: {}", DEFAULT_TENANT);
        return DEFAULT_TENANT;
    }
    
    /**
     * Extrae el subdominio de la URL
     */
    private String extractSubdomain(String serverName) {
        if (!StringUtils.hasText(serverName)) {
            log.debug("[SubdomainTenantResolver] extractSubdomain: serverName vacío");
            return null;
        }

        // Para facturita.com
        if (serverName.endsWith("." + PRODUCTION_DOMAIN)) {
            String subdomain = serverName.substring(0, serverName.indexOf("." + PRODUCTION_DOMAIN));
            log.debug("[SubdomainTenantResolver] extractSubdomain: subdominio producción extraído: {}", subdomain);
            return subdomain.toLowerCase();
        }

        // Para desarrollo con subdominios locales (ej: cna.localhost)
        if (serverName.endsWith("." + DEV_DOMAIN)) {
            String subdomain = serverName.substring(0, serverName.indexOf("." + DEV_DOMAIN));
            log.debug("[SubdomainTenantResolver] extractSubdomain: subdominio desarrollo extraído: {}", subdomain);
            return subdomain.toLowerCase();
        }

        log.debug("[SubdomainTenantResolver] extractSubdomain: no se encontró subdominio");
        return null;
    }
    
    /**
     * Valida que el nombre del tenant sea válido
     */
    private boolean isValidTenant(String tenant) {
        if (!StringUtils.hasText(tenant)) {
            log.debug("[SubdomainTenantResolver] isValidTenant: tenant vacío");
            return false;
        }
        // Validar longitud
        if (tenant.length() < 2 || tenant.length() > 50) {
            log.debug("[SubdomainTenantResolver] isValidTenant: longitud inválida para tenant: {}", tenant);
            return false;
        }
        // Validar patrón
        boolean matches = TENANT_PATTERN.matcher(tenant).matches();
        log.debug("[SubdomainTenantResolver] isValidTenant: patrón válido? {} para tenant: {}", matches, tenant);
        return matches;
    }
    
    /**
     * Determina si una URL corresponde al tenant administrador
     */
    public boolean isAdminTenant(String tenant) {
        return DEFAULT_TENANT.equals(tenant);
    }
}
