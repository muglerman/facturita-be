package com.cna.facturita.core.loader.tenant;

import com.cna.facturita.core.model.tenant.UsuarioTenant;
import com.cna.facturita.core.repository.tenant.UsuarioRepositoryTenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDataLoader {

    private static final Logger log = LoggerFactory.getLogger(UsuarioDataLoader.class);
    
    @Qualifier("UsuarioRepositoryTenant")
    private final UsuarioRepositoryTenant usuarioRepositoryTenant;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDataLoader(
            UsuarioRepositoryTenant usuarioRepositoryTenant,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepositoryTenant = usuarioRepositoryTenant;
        this.passwordEncoder = passwordEncoder;
    }

    public void cargaInicial() {
        log.info("=== Iniciando carga de datos demo para Usuarios ===");

        if (usuarioRepositoryTenant.count() > 0) {
            log.info("Ya existen usuarios en la base de datos. Saltando creación de datos demo.");
            return;
        }

        Object[][] data = {
                { "Kotosh La Victoria", "transporteskotoshcargo@gmail.com", "kotosklim", "12345678", 1, "ADMIN", 1 },
                { "Kotosh PCL", "1975maximina@gmail.com", "kotoshpcl", "12345678", 2, "ADMIN", 1 },
                { "Kotosh ATE", "negocioskotosh@gmail.com", "kotoshiqt", "12345678", 3, "ADMIN", 1 }
        };

        List<UsuarioTenant> usuariosTenant= new ArrayList<>();
        for (Object[] row : data) {

            UsuarioTenant usuarioTenant = new UsuarioTenant();
            usuarioTenant.setNombre(row[0].toString());
            usuarioTenant.setEmail(row[1].toString());
            usuarioTenant.setUsuario(row[2].toString());
            usuarioTenant.setPassword(passwordEncoder.encode(row[3].toString()));
            usuarioTenant.setEstablecimientoId(row[4] != null ? Integer.valueOf(row[4].toString()) : null);
            usuarioTenant.setTipoUsuario(row[5] != null ? UsuarioTenant.TipoUsuario.valueOf(row[5].toString()) : null);
            usuarioTenant.setEstado(row[6] != null ? Integer.valueOf(row[6].toString()) == 1 : null);

            usuariosTenant.add(usuarioTenant);
        }

        for (UsuarioTenant usuarioTenant : usuariosTenant) {
            try {
                usuarioRepositoryTenant.save(usuarioTenant);
                log.info("✓ Usuario creado: {} - {}", usuarioTenant.getId(), usuarioTenant.getNombre());
            } catch (Exception e) {
                log.error("Error al crear usuario {}: {}", usuarioTenant.getId(), e.getMessage());
            }
        }

        log.info("Usuarios de demostración creados exitosamente. Total: {}", usuarioRepositoryTenant.count());
    }
}
