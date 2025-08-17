package com.cna.facturita.multitenant.filter;

import com.cna.facturita.multitenant.context.TenantContext;
import com.cna.facturita.multitenant.resolver.SubdomainTenantResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.core.Ordered;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Filtro para establecer el tenant en el contexto por request
 */
@Slf4j
@Component
public class TenantFilter extends OncePerRequestFilter implements Ordered {
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
    @Autowired
    private SubdomainTenantResolver tenantResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull jakarta.servlet.http.HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String tenant = request.getHeader("x-tenant");
        if (tenant == null || tenant.isEmpty()) {
            tenant = tenantResolver.resolveTenant(request);
        }

        log.debug("[TenantFilter] Host: {}, Tenant resuelto: {}", request.getServerName(), tenant);
        log.debug("[TenantFilter] Asignando tenant al contexto: {}", tenant);
        TenantContext.setCurrentTenant(tenant);
        try {
            filterChain.doFilter(request, response);
        } finally {
            log.debug("[TenantFilter] Limpiando contexto de tenant. Valor actual: {}", TenantContext.getCurrentTenant());
            TenantContext.clear();
            log.debug("[TenantFilter] Contexto de tenant limpiado.");
        }
    }
}
