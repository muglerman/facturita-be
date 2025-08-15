package com.cna.facturita.multitenant.filter;

import com.cna.facturita.multitenant.context.TenantContext;
import com.cna.facturita.multitenant.resolver.SubdomainTenantResolver;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Filtro para establecer el tenant en el contexto por request
 */
@Slf4j
@Component
public class TenantFilter implements Filter {
    @Autowired
    private SubdomainTenantResolver tenantResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenant = httpRequest.getHeader("x-tenant");
        if (tenant == null || tenant.isEmpty()) {
            tenant = tenantResolver.resolveTenant(httpRequest);
        }

        log.debug("[TenantFilter] Host: {}, Tenant resuelto: {}", httpRequest.getServerName(), tenant);

        TenantContext.setCurrentTenant(tenant);
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
