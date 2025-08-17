package com.cna.facturita.api.config;

import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;
import com.cna.facturita.multitenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    @Qualifier("UsuarioRepository")
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final MultiTenantDataLoader multiTenantDataLoader;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void run(String... args) throws Exception {
        log.info("[DataLoader] Iniciando carga de datos iniciales...");
        log.info("[DataLoader] Esquema actual: {}", TenantContext.getCurrentTenant());
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS cna");
        try {

            ejecutarScriptDDL("db/ddl/schema.sql", TenantContext.getCurrentTenant());
            // Verificar si ya existen usuarios en el esquema actual
            if (usuarioRepository.count() > 0) {
                log.info("[DataLoader] Ya existen usuarios en el esquema {}. Omitiendo carga inicial.",
                        TenantContext.getCurrentTenant());
                return;
            }

            log.info("[DataLoader] No se encontraron usuarios en esquema {}. Creando usuario administrador...",
                    TenantContext.getCurrentTenant());
            crearUsuarioAdministrador();

            // Cargar datos específicos del tenant - borrar luego

            //jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS demo");
            TenantContext.clear();
            TenantContext.setCurrentTenant("demo");
                log.info("[DataLoader] Ejecutando cargaInicialTenant() para esquema demo...");
            TransactionTemplate template = new TransactionTemplate(transactionManager);
            template.execute(status -> {
                    log.info("[DataLoader] Dentro de la transacción, antes de cargaInicialTenant()");
                multiTenantDataLoader.cargaInicialTenant();
                    log.info("[DataLoader] Después de ejecutar cargaInicialTenant()");
                return null;
            });
                log.info("[DataLoader] Finalizó cargaInicialTenant() para esquema demo");

            log.info("[DataLoader] Carga de datos iniciales completada en esquema {}.",
                    TenantContext.getCurrentTenant());
        } finally {
            // Limpiar el contexto
            //TenantContext.clear();
        }
    }

    private void crearUsuarioAdministrador() {
        Usuario admin = new Usuario();
        admin.setNombre("Administrador Sistema");
        admin.setEmail("admin@facturita.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFechaVerificacionCorreo(LocalDateTime.now());

        usuarioRepository.save(admin);

        log.info("[DataLoader] Usuario administrador creado en esquema admin:");
        log.info("  - Email: {}", admin.getEmail());
        log.info("  - Contraseña: admin123 (CAMBIAR EN PRODUCCIÓN)");
        log.info("  - Nombre: {}", admin.getNombre());
        log.warn("  - IMPORTANTE: Cambiar la contraseña por defecto en producción");
    }

    private void ejecutarScriptDDL(String scriptPath, String tenantName) {
        try {
            String sql = Files.lines(new ClassPathResource(scriptPath).getFile().toPath())
                    .collect(Collectors.joining("\n"))
                    .replace("${tenant}", tenantName);
            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    jdbcTemplate.execute(statement);
                }
            }
            log.info("[MultiTenantDataLoader] DDL ejecutado para tenant: {}", tenantName);
        } catch (Exception e) {
            log.error("[MultiTenantDataLoader] Error ejecutando DDL para tenant {}: {}", tenantName, e.getMessage());
        }
    }
}
