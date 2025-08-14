package com.cna.facturita.multitenant.config;

import com.cna.facturita.multitenant.interceptor.TenantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración web para registrar el interceptor de tenant
 */
@Configuration
public class MultiTenantWebConfig implements WebMvcConfigurer {
    
    private final TenantInterceptor tenantInterceptor;
    
    public MultiTenantWebConfig(TenantInterceptor tenantInterceptor) {
        this.tenantInterceptor = tenantInterceptor;
    }
    
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/health", "/actuator/**"); // Excluir endpoints de health
    }
}
