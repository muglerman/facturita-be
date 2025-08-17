package com.cna.facturita.multitenant.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para registrar el bean de MultiTenantConnectionProvider
 */
@Configuration
public class HibernateMultiTenantProviderConfig {
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider(MultiTenantDataSourceProvider provider) {
        System.out.println("[DEBUG] multiTenantConnectionProvider bean creado, provider: " + (provider != null));
        MultiTenantConnectionProviderImpl impl = new MultiTenantConnectionProviderImpl();
        MultiTenantConnectionProviderImpl.setDataSourceProvider(provider);
        return impl;
    }
}
