package com.cna.facturita.api.config;


import com.cna.facturita.core.loader.tenant.ClienteDataLoader;
import com.cna.facturita.core.loader.tenant.DepartamentoDataLoader;
import com.cna.facturita.core.loader.tenant.DistritoDataLoader;
import com.cna.facturita.core.loader.tenant.PaisDataLoader;
import com.cna.facturita.core.loader.tenant.ProvinciaDataLoader;
import com.cna.facturita.core.loader.tenant.TipoDocumentoDataLoader;
import com.cna.facturita.core.loader.tenant.UsuarioDataLoader;
//import com.cna.facturita.core.loader.tenant.C
import com.cna.facturita.multitenant.context.TenantContext;

// import com.cna.facturita.core.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.io.ClassPathResource;
import java.nio.file.Files;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 * Carga datos iniciales específicos para multitenant.
 * Se ejecuta después del DataLoader básico.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MultiTenantDataLoader {

    private final ClienteDataLoader clienteDataLoader;
    private final DepartamentoDataLoader departamentoDataLoader;
    private final DistritoDataLoader distritoDataLoader;
    private final PaisDataLoader paisDataLoader;
    private final ProvinciaDataLoader provinciaDataLoader;
    private final TipoDocumentoDataLoader tipoDocumentoDataLoader;
    private final UsuarioDataLoader usuarioDataLoader;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Crea usuarios específicos para cada tenant/empresa
     */
    public void cargaInicialTenant() {
    log.info("[MultiTenantDataLoader] Iniciando carga de datos iniciales para tenants...");
    log.info("[MultiTenantDataLoader] Esquema actual: {}", TenantContext.getCurrentTenant());

    // Ejecutar DDL dinámico para el esquema actual
    ejecutarScriptDDL("db/ddl/schema_tenant.sql", TenantContext.getCurrentTenant());

    // Cambiar el search_path al esquema del tenant
    String setSearchPath = String.format("SET search_path TO %s;", TenantContext.getCurrentTenant());
    log.info("[MultiTenantDataLoader] Ejecutando: {}", setSearchPath);
    jdbcTemplate.execute(setSearchPath);

    tipoDocumentoDataLoader.cargaInicial();
    departamentoDataLoader.cargaInicial();
    provinciaDataLoader.cargaInicial();
    distritoDataLoader.cargaInicial();
    paisDataLoader.cargaInicial();
    clienteDataLoader.cargaInicial();
    usuarioDataLoader.cargaInicial();

    log.info("[MultiTenantDataLoader] Carga de datos iniciales para tenants completada.");
    }

    private void ejecutarScriptDDL(String scriptPath, String tenantName) {
        try {
            log.info("[MultiTenantDataLoader] DDL ejecutando para tenant: {}", tenantName);
            String sql = Files.lines(new ClassPathResource(scriptPath).getFile().toPath())
                .collect(Collectors.joining("\n"))
                .replace("${tenant}", tenantName);
            log.info("[MultiTenantDataLoader] SQL generado para tenant {}:\n{}", tenantName, sql);
            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    log.info("[MultiTenantDataLoader] Ejecutando statement:\n{}", statement);
                    jdbcTemplate.execute(statement);
                }
            }
            log.info("[MultiTenantDataLoader] DDL ejecutado para tenant: {}", tenantName);
        } catch (Exception e) {
            log.error("[MultiTenantDataLoader] Error ejecutando DDL para tenant {}: {}", tenantName, e.getMessage());
        }
    }
}
