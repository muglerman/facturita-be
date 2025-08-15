package com.cna.facturita.multitenant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import com.cna.facturita.multitenant.context.TenantContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Configura un RoutingDataSource para delegar a cada DataSource según el tenant
 */
@Slf4j
@Configuration
public class DataSourceRoutingConfig {

    @Autowired
    private MultiTenantDataSourceProvider multiTenantDataSourceProvider;

    @Bean
    public DataSource dataSource() {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                log.debug("[MultiTenantDataSourceProvider] Usando esquema: {}", TenantContext.getCurrentTenant());
                return TenantContext.getCurrentTenant();
            }
        };
        // Inicialmente vacío, se delega la creación al provider
        routingDataSource.setTargetDataSources(new HashMap<>());
        routingDataSource.setDefaultTargetDataSource(multiTenantDataSourceProvider.getDataSource(TenantContext.DEFAULT_TENANT));
        return routingDataSource;
    }
}
