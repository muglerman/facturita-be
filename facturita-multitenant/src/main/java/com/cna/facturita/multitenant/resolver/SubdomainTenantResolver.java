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
    
    private static final String DEFAULT_TENANT = "admin";
    private static final String PRODUCTION_DOMAIN = "facturitapro.com";
    private static final String DEV_DOMAIN = "localhost";
    
    // Patrón para validar nombres de tenant (solo letras, números y guiones)
    private static final Pattern TENANT_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");
    
    /**
     * Resuelve el tenant desde la URL del request
     * 
     * Ejemplos:
     * - localhost:8080 -> admin (desarrollo)
     * - facturita.com -> admin (producción)
     * - cna.facturita.com -> cna
     * - empresa1.facturita.com -> empresa1
     * - localhost -> admin (desarrollo sin puerto)
     */
    public String resolveTenant(HttpServletRequest request) {
        String serverName = request.getServerName();
        
        if (serverName == null) {
            return DEFAULT_TENANT;
        }
        
        // Desarrollo: localhost o localhost:puerto
        if (serverName.equals(DEV_DOMAIN) || serverName.equals("127.0.0.1")) {
            return DEFAULT_TENANT;
        }
        
        // Producción: dominio principal
        if (serverName.equals(PRODUCTION_DOMAIN)) {
            return DEFAULT_TENANT;
        }
        
        // Subdominios
        String tenant = extractSubdomain(serverName);
        
        // Validar que el tenant sea válido
        if (isValidTenant(tenant)) {
            return tenant;
        }
        
        // Si no es válido, usar default
        return DEFAULT_TENANT;
    }
    
    /**
     * Extrae el subdominio de la URL
     */
    private String extractSubdomain(String serverName) {
        if (!StringUtils.hasText(serverName)) {
            return null;
        }
        
        // Para facturita.com
        if (serverName.endsWith("." + PRODUCTION_DOMAIN)) {
            String subdomain = serverName.substring(0, serverName.indexOf("." + PRODUCTION_DOMAIN));
            return subdomain.toLowerCase();
        }
        
        // Para desarrollo con subdominios locales (ej: cna.localhost)
        if (serverName.endsWith("." + DEV_DOMAIN)) {
            String subdomain = serverName.substring(0, serverName.indexOf("." + DEV_DOMAIN));
            return subdomain.toLowerCase();
        }
        
        return null;
    }
    
    /**
     * Valida que el nombre del tenant sea válido
     */
    private boolean isValidTenant(String tenant) {
        if (!StringUtils.hasText(tenant)) {
            return false;
        }
        // Validar longitud
        if (tenant.length() < 2 || tenant.length() > 50) {
            return false;
        }
        // Validar patrón
        return TENANT_PATTERN.matcher(tenant).matches();
    }
    
    /**
     * Determina si una URL corresponde al tenant administrador
     */
    public boolean isAdminTenant(String tenant) {
        return DEFAULT_TENANT.equals(tenant);
    }
}
