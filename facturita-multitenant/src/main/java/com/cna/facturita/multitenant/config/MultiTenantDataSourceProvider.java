package com.cna.facturita.multitenant.config;

import com.cna.facturita.multitenant.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maneja múltiples DataSources para cada tenant
 */
@Component
public class MultiTenantDataSourceProvider {
    
    private static final Logger log = LoggerFactory.getLogger(MultiTenantDataSourceProvider.class);
    
    @Value("${spring.datasource.url}")
    private String baseUrl;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    
    /**
     * Obtiene el DataSource para el tenant actual
     */
    public DataSource getDataSource() {
        String tenant = TenantContext.getCurrentTenant();
        return getDataSource(tenant);
    }
    /**
     * Obtiene cualquier DataSource disponible (por ejemplo, el primero configurado)
     */
    public DataSource getAnyDataSource() {
        if (dataSources.isEmpty()) {
            throw new IllegalStateException("No hay DataSources configurados");
        }
        return dataSources.values().iterator().next();
    }
    
    /**
     * Obtiene el DataSource para un tenant específico
     */
    public DataSource getDataSource(String tenant) {
        return dataSources.computeIfAbsent(tenant, this::createDataSource);
    }
    
    /**
     * Crea un nuevo DataSource para un tenant específico
     */
    private DataSource createDataSource(String tenant) {
        log.info("Creando DataSource para tenant: {}", tenant);
        
        // Construir URL con el esquema específico del tenant
        String schemaUrl = buildUrlWithSchema(tenant);
        
        return DataSourceBuilder.create()
                .url(schemaUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
    
    /**
     * Construye la URL de conexión con el esquema específico del tenant
     */
    private String buildUrlWithSchema(String tenant) {
        // Obtener la URL base sin parámetros
        String baseUrlWithoutParams = baseUrl.split("\\?")[0];
        
        // Agregar el esquema específico del tenant
        return baseUrlWithoutParams + "?currentSchema=" + tenant;
    }
    
    /**
     * Verifica si existe un DataSource para el tenant
     */
    public boolean hasDataSource(String tenant) {
        return dataSources.containsKey(tenant);
    }
    
    /**
     * Obtiene todos los tenants configurados
     */
    public Set<String> getConfiguredTenants() {
        return dataSources.keySet();
    }
    
    /**
     * Limpia el cache de DataSources (útil para testing)
     */
    public void clearCache() {
        dataSources.clear();
    }
}
