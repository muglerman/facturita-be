package com.cna.facturita.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.cna.facturita.multitenant.config.MultiTenantDataSourceProvider;

@SpringBootApplication(scanBasePackages = { "com.cna.facturita" })
@EnableJpaRepositories(basePackages = "com.cna.facturita.core.repository")
@EntityScan(basePackages = "com.cna.facturita.core.model")
public class FacturitaApiApplication implements CommandLineRunner {

    @Autowired
    private MultiTenantDataSourceProvider multiTenantDataSourceProvider;

    public static void main(String[] args) {
        SpringApplication.run(FacturitaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica que el bean está disponible al arrancar
        System.out.println("[DEBUG] MultiTenantDataSourceProvider inicializado: " + (multiTenantDataSourceProvider != null));
    }
}
