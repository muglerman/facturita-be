package com.cna.facturita.api.config;

import com.cna.facturita.core.model.Usuario;
// import com.cna.facturita.core.model.Empresa;
import com.cna.facturita.core.repository.UsuarioRepository;
// import com.cna.facturita.core.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Carga datos iniciales específicos para multitenant.
 * Se ejecuta después del DataLoader básico.
 */
@Slf4j
@Component  // Habilitamos de nuevo después de solucionar el problema del esquema
@RequiredArgsConstructor
@Order(2) // Se ejecuta después del DataLoader básico
public class MultiTenantDataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    // Comentamos temporalmente EmpresaRepository para evitar problemas de dependencias
    // private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("[MultiTenantDataLoader] Iniciando carga de datos multitenant...");
        
        // Comentamos temporalmente hasta tener el EmpresaRepository funcionando
        // crearEmpresasDemostracion();
        crearUsuariosPorTenant();
        
        log.info("[MultiTenantDataLoader] Carga de datos multitenant completada.");
    }

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
    private void crearUsuariosPorTenant() {
        // Solo crear si no hay usuarios tenant específicos
        long usuariosExistentes = usuarioRepository.count();
        if (usuariosExistentes > 1) { // Más que solo el admin
            log.info("[MultiTenantDataLoader] Ya existen usuarios tenant. Omitiendo creación.");
            return;
        }

        log.info("[MultiTenantDataLoader] Creando usuarios por tenant...");

        // Usuario para Tech Solutions
        Usuario usuarioTech = new Usuario();
        usuarioTech.setNombre("Gerente Tech Solutions");
        usuarioTech.setEmail("gerente@techsolutions.com");
        usuarioTech.setPassword(passwordEncoder.encode("tech123"));
        usuarioTech.setFechaVerificacionCorreo(LocalDateTime.now());

        // Usuario para Restaurante
        Usuario usuarioRestaurante = new Usuario();
        usuarioRestaurante.setNombre("Administrador Restaurante");
        usuarioRestaurante.setEmail("admin@restaurante.com");
        usuarioRestaurante.setPassword(passwordEncoder.encode("resto123"));
        usuarioRestaurante.setFechaVerificacionCorreo(LocalDateTime.now());

        // Usuario para Comercial
        Usuario usuarioComercial = new Usuario();
        usuarioComercial.setNombre("Supervisor Comercial");
        usuarioComercial.setEmail("supervisor@comercial.com");
        usuarioComercial.setPassword(passwordEncoder.encode("comercial123"));
        usuarioComercial.setFechaVerificacionCorreo(LocalDateTime.now());

        usuarioRepository.save(usuarioTech);
        usuarioRepository.save(usuarioRestaurante);
        usuarioRepository.save(usuarioComercial);

        log.info("[MultiTenantDataLoader] Usuarios tenant creados:");
        log.info("  - Tech: {} ({})", usuarioTech.getNombre(), usuarioTech.getEmail());
        log.info("  - Restaurante: {} ({})", usuarioRestaurante.getNombre(), usuarioRestaurante.getEmail());
        log.info("  - Comercial: {} ({})", usuarioComercial.getNombre(), usuarioComercial.getEmail());
        
        log.warn("[MultiTenantDataLoader] === CREDENCIALES DE PRUEBA ===");
        log.warn("  - tech123, resto123, comercial123");
        log.warn("  - CAMBIAR EN PRODUCCIÓN");
    }
}
