package com.cna.facturita.core.loader;

import com.cna.facturita.core.model.Configuracion;
import com.cna.facturita.core.repository.ConfiguracionRepository;

import java.time.LocalDateTime;

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
        config1.setId(1);
        config1.setAdminBloqueado(false); // locked_admin = 0
        config1.setCertificado(null); // certificate
        config1.setEnvioSoapId("01"); // soap_send_id
        config1.setFechaCreacion(LocalDateTime.now());
        config1.setFechaActualizacion(LocalDateTime.now());
        config1.setTipoSoapId("01");
        config1.setSoapUsername(null);
        config1.setSoapPassword(null);
        config1.setSoapUrl(null);
        config1.setTokenPublicCulqui(null);
        config1.setTokenPrivateCulqui(null);
        config1.setUrlApiRuc("https://apiperu.dev");
        config1.setTokenApiRuc("4b297f3cf07f893870d7d3db9b22e10ea47a8340e2bef32a3b8ca94153ae5a1c");
        config1.setUseLoginGlobal(false);
        config1.setLogin("{\\\"type\\\":\\\"image\\\",\\\"image\\\":\\\"http:\\/\\/localhost\\/images\\/login-v2.svg\\\",\\\"position_form\\\":\\\"right\\\",\\\"show_logo_in_form\\\":false,\\\"position_logo\\\":\\\"top-left\\\",\\\"show_socials\\\":false}");
        config1.setWhatsappHabilitado(true); // enable_whatsapp = 1
        config1.setRegexPasswordCliente(null);
        // El campo 'certificate' y otros nulos se dejan como null

        try {
            Configuracion savedConfig = configuracionRepository.save(config1);
            log.info("✓ Configuración creada: id={}, adminBloqueado={}, envioSoapId={}, whatsappHabilitado={}",
                    savedConfig.getId(),
                    savedConfig.getAdminBloqueado(),
                    savedConfig.getEnvioSoapId(),
                    savedConfig.getWhatsappHabilitado());
        } catch (Exception e) {
            log.error("Error al crear configuración id {}: {}", config1.getId(), e.getMessage());
        }

        log.info("Configuraciones de demostración creadas exitosamente. Total: {}", 1);
    }
}
