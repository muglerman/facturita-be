package com.cna.facturita.core.loader;

import com.cna.facturita.core.model.Configuracion;
import com.cna.facturita.core.repository.ConfiguracionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Carga datos de demostración para configuraciones del sistema.
 * Se ejecuta después del DataLoader de planes para asegurar que la base de
 * datos esté configurada.
 */
@Component
@Order(3)
public class ConfiguracionDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConfiguracionDataLoader.class);
    private final ConfiguracionRepository configuracionRepository;

    public ConfiguracionDataLoader(ConfiguracionRepository configuracionRepository) {
        this.configuracionRepository = configuracionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("=== Iniciando carga de datos demo para Configuraciones ===");
        try {
            crearConfiguracionesDemo();
            log.info("=== Datos demo de Configuraciones cargados exitosamente ===");
        } catch (Exception e) {
            log.error("Error al cargar datos demo de configuraciones: {}", e.getMessage(), e);
        }
    }

    private void crearConfiguracionesDemo() {
        if (configuracionRepository.count() > 0) {
            log.info("Ya existen configuraciones en la base de datos. Saltando creación de datos demo.");
            return;
        }

        log.info("Creando configuraciones de demostración...");

        Configuracion config1 = new Configuracion();
        config1.setAdminBloqueado(false);
        config1.setCertificado("certificado-demo");
        config1.setEnvioSoapId("01");
        config1.setSoapTypeId("01");
        config1.setSoapUsername("usuario-demo");
        config1.setSoapPassword("password-demo");
        config1.setSoapUrl("https://soap.demo.com");
        config1.setTokenPublicCulqui("token-publico-demo");
        config1.setTokenPrivateCulqui("token-privado-demo");
        config1.setUrlApiRuc("https://apiruc.demo.com");
        config1.setTokenApiRuc("token-apiruc-demo");
        config1.setUseLoginGlobal(false);
        config1.setLogin("login-demo");
        // config1.setApkUrl("https://apk.demo.com"); // Si existe en el modelo
        config1.setHabilitarWhatsapp(true);
        config1.setRegexPasswordClient(false);
        // config1.setTenantShowAds(false); // Si existe en el modelo
        // config1.setTenantImageAds("https://img.demo.com/ads.png"); // Si existe en el
        // modelo

        try {
            Configuracion savedConfig = configuracionRepository.save(config1);
            log.info("✓ Configuración creada: id={}, adminBloqueado={}, envioSoapId={}, habilitarWhatsapp={}",
                    savedConfig.getId(),
                    savedConfig.getAdminBloqueado(),
                    savedConfig.getEnvioSoapId(),
                    savedConfig.getHabilitarWhatsapp());
        } catch (Exception e) {
            log.error("Error al crear configuración id {}: {}", config1.getId(), e.getMessage());
        }

        log.info("Configuraciones de demostración creadas exitosamente. Total: {}", 1);
    }
}
