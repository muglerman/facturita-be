package com.cna.facturita.multitenant.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * Implementación de MultiTenantConnectionProvider para Hibernate
 * que delega en MultiTenantDataSourceProvider
 */

public class MultiTenantConnectionProviderImpl
        implements MultiTenantConnectionProvider<String>, ApplicationContextAware {
    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        DataSource dataSource = dataSourceProvider.getDataSource(tenantIdentifier);
        return dataSource.getConnection();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return MultiTenantConnectionProvider.class.equals(unwrapType) ||
                MultiTenantConnectionProviderImpl.class.isAssignableFrom(unwrapType);
    }

    private static MultiTenantDataSourceProvider dataSourceProvider;
    private static ApplicationContext context;

    // Constructor sin argumentos para Hibernate
    public MultiTenantConnectionProviderImpl() {
        System.out.println("[DEBUG] MultiTenantConnectionProviderImpl constructor llamado");
    }

    public static void setDataSourceProvider(MultiTenantDataSourceProvider provider) {
        System.out.println("[DEBUG] setDataSourceProvider llamado: " + (provider != null));
        dataSourceProvider = provider;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext ctx) {
        context = ctx;
        if (dataSourceProvider == null) {
            dataSourceProvider = context.getBean(MultiTenantDataSourceProvider.class);
        }
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        DataSource dataSource = dataSourceProvider.getAnyDataSource();
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> unwrapType) {
        if (isUnwrappableAs(unwrapType)) {
            return (T) this;
        }
        throw new IllegalArgumentException("Cannot unwrap to " + unwrapType.getName());
    }
}
