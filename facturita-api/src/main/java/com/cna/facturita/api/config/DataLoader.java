package com.cna.facturita.api.config;

import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;
import com.cna.facturita.multitenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("[DataLoader] Iniciando carga de datos iniciales...");
        
        // Establecer contexto admin para crear datos iniciales
        TenantContext.setCurrentTenant("admin");
        
        try {
            // Verificar si ya existen usuarios en el esquema admin
            if (usuarioRepository.count() > 0) {
                log.info("[DataLoader] Ya existen usuarios en el esquema admin. Omitiendo carga inicial.");
                return;
            }

            log.info("[DataLoader] No se encontraron usuarios en esquema admin. Creando usuario administrador...");
            crearUsuarioAdministrador();
            
            log.info("[DataLoader] Carga de datos iniciales completada en esquema admin.");
        } finally {
            // Limpiar el contexto
            TenantContext.clear();
        }
    }

    private void crearUsuarioAdministrador() {
        Usuario admin = new Usuario();
        admin.setNombre("Administrador Sistema");
        admin.setEmail("admin@facturita.com");
        admin.setPassword(passwordEncoder.encode("admin123")); // Cambiar por una contraseña más segura
        admin.setFechaVerificacionCorreo(LocalDateTime.now()); // Marcamos como verificado
        // Los campos fechaDeCreacion y fechaDeActualizacion se asignan automáticamente por @PrePersist

        usuarioRepository.save(admin);
        
        log.info("[DataLoader] Usuario administrador creado en esquema admin:");
        log.info("  - Email: {}", admin.getEmail());
        log.info("  - Contraseña: admin123 (CAMBIAR EN PRODUCCIÓN)");
        log.info("  - Nombre: {}", admin.getNombre());
        log.warn("  - IMPORTANTE: Cambiar la contraseña por defecto en producción");
    }
}
