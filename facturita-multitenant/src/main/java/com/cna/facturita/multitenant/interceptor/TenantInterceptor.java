package com.cna.facturita.multitenant.interceptor;

import com.cna.facturita.multitenant.context.TenantContext;
import com.cna.facturita.multitenant.resolver.SubdomainTenantResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor que establece el contexto del tenant en cada request
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {
    
    private static final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);
    
    private final SubdomainTenantResolver tenantResolver;
    
    public TenantInterceptor(SubdomainTenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenant = tenantResolver.resolveTenant(request);
        TenantContext.setCurrentTenant(tenant);
        
        log.debug("Request from {} -> Tenant: {}", request.getServerName(), tenant);
        
        // Agregar header de respuesta para debugging (opcional)
        response.setHeader("X-Tenant", tenant);
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Limpiar el contexto después del request
        TenantContext.clear();
    }
}
