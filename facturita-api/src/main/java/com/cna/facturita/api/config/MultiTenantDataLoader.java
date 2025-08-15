package com.cna.facturita.api.config;

import com.cna.facturita.core.model.Usuario;
// import com.cna.facturita.core.model.Empresa;
import com.cna.facturita.core.repository.UsuarioRepository;
// import com.cna.facturita.core.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Carga datos iniciales específicos para multitenant.
 * Se ejecuta después del DataLoader básico.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MultiTenantDataLoader {

    private final UsuarioRepository usuarioRepository;
    // Comentamos temporalmente EmpresaRepository para evitar problemas de dependencias
    // private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    

    /**
     * Crea empresas de demostración para testing multitenant
     * TEMPORALMENTE DESHABILITADO - problemas con dependencias
     */
    /*
    private void crearEmpresasDemostracion() {
        if (empresaRepository.count() > 0) {
            log.info("[MultiTenantDataLoader] Ya existen empresas. Omitiendo creación de demos.");
            return;
        }

        log.info("[MultiTenantDataLoader] Creando empresas de demostración...");
        
        // Empresa 1: Tecnología
        Empresa empresaDemo1 = new Empresa();
        empresaDemo1.setRuc("20123456789");
        empresaDemo1.setRazonSocial("Tech Solutions S.A.C.");
        
        // Empresa 2: Restaurante
        Empresa empresaDemo2 = new Empresa();
        empresaDemo2.setRuc("20987654321");
        empresaDemo2.setRazonSocial("Restaurante El Buen Sabor E.I.R.L.");
        
        // Empresa 3: Comercio
        Empresa empresaDemo3 = new Empresa();
        empresaDemo3.setRuc("20456789123");
        empresaDemo3.setRazonSocial("Comercial Los Andes S.R.L.");
        
        empresaRepository.save(empresaDemo1);
        empresaRepository.save(empresaDemo2);
        empresaRepository.save(empresaDemo3);
        
        log.info("[MultiTenantDataLoader] Empresas demo creadas:");
        log.info("  - Empresa 1: {} (RUC: {})", empresaDemo1.getRazonSocial(), empresaDemo1.getRuc());
        log.info("  - Empresa 2: {} (RUC: {})", empresaDemo2.getRazonSocial(), empresaDemo2.getRuc());
        log.info("  - Empresa 3: {} (RUC: {})", empresaDemo3.getRazonSocial(), empresaDemo3.getRuc());
    }
    */

    /**
     * Crea usuarios específicos para cada tenant/empresa
     */
    private void cargarDatosDemo() {
        // Solo crear si no hay usuarios tenant específicos
        long usuariosExistentes = usuarioRepository.count();
        if (usuariosExistentes > 1) { // Más que solo el admin
            log.info("[MultiTenantDataLoader] Ya existen usuarios tenant. Omitiendo creación.");
            return;
        }

        log.info("[MultiTenantDataLoader] Creando usuarios por tenant...");

        // Usuario para Demo Enterprise
        Usuario usuarioGerente = new Usuario();
        usuarioGerente.setNombre("Gerente Demo  Enterprise");
        usuarioGerente.setEmail("gerente@demo.com");
        usuarioGerente.setPassword(passwordEncoder.encode("demo123"));
        usuarioGerente.setFechaVerificacionCorreo(LocalDateTime.now());

        // Usuario 2 para Demo Enterprise
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setNombre("Administrador Demo Enterprise");
        usuarioAdmin.setEmail("admin@demo.com");
        usuarioAdmin.setPassword(passwordEncoder.encode("resto123"));
        usuarioAdmin.setFechaVerificacionCorreo(LocalDateTime.now());

        // Usuario para   Demo Enterprise
        Usuario usuarioNormal = new Usuario();
        usuarioNormal.setNombre("Supervisor Demo Enterprise");
        usuarioNormal.setEmail("supervisor@demo.com");
        usuarioNormal.setPassword(passwordEncoder.encode("comercial123"));
        usuarioNormal.setFechaVerificacionCorreo(LocalDateTime.now());

        usuarioRepository.save(usuarioGerente);
        usuarioRepository.save(usuarioAdmin);
        usuarioRepository.save(usuarioNormal);

        log.info("[MultiTenantDataLoader] Usuarios tenant creados:");
        log.info("  - Gerente: {} ({})", usuarioGerente.getNombre(), usuarioGerente.getEmail());
        log.info("  - Administrador: {} ({})", usuarioAdmin.getNombre(), usuarioAdmin.getEmail());
        log.info("  - Supervisor: {} ({})", usuarioNormal.getNombre(), usuarioNormal.getEmail());

        log.warn("[MultiTenantDataLoader] === CREDENCIALES DE PRUEBA ===");
        log.warn("  - gerente123, admin123, supervisor123");
    }
}
