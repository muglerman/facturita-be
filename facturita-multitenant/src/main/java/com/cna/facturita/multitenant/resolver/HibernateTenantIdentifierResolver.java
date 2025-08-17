package com.cna.facturita.multitenant.resolver;

import com.cna.facturita.multitenant.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Resolver para Hibernate que obtiene el tenant actual desde TenantContext
 */
@Component
public class HibernateTenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {
    @Override
    public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContext.getCurrentTenant();
    org.slf4j.LoggerFactory.getLogger(HibernateTenantIdentifierResolver.class).debug("[HibernateTenantIdentifierResolver] resolveCurrentTenantIdentifier: {}", tenant);
    return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
