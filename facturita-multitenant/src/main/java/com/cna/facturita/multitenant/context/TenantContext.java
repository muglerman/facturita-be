package com.cna.facturita.multitenant.context;

/**
 * Maneja el contexto del tenant actual usando ThreadLocal
 */
public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    public static final String DEFAULT_TENANT = "cna";

    public static String getCurrentTenant() {
    String tenant = CURRENT_TENANT.get();
    org.slf4j.LoggerFactory.getLogger(TenantContext.class).debug("[TenantContext] getCurrentTenant tenant: {}", tenant);
    String result = tenant != null ? tenant : DEFAULT_TENANT;
    org.slf4j.LoggerFactory.getLogger(TenantContext.class).debug("[TenantContext] getCurrentTenant result: {}", result);
    return result;
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
        org.slf4j.LoggerFactory.getLogger(TenantContext.class).debug("[TenantContext] setCurrentTenant: {}", tenant);
    }

    public static void clear() {
        org.slf4j.LoggerFactory.getLogger(TenantContext.class).debug("[TenantContext] clear() llamado. Tenant anterior: {}", CURRENT_TENANT.get());
        CURRENT_TENANT.remove();
    }
}