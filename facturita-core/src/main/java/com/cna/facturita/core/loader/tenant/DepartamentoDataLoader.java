package com.cna.facturita.core.loader.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.core.repository.tenant.DepartamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Carga datos de demostración para departamentos.
 * Se ejecuta después de los loaders principales.
 */
@Component
@Order(3)
public class DepartamentoDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoDataLoader.class);

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoDataLoader(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("=== Iniciando carga de datos demo para Departamentos ===");

        if (departamentoRepository.count() > 0) {
            log.info("Ya existen departamentos en la base de datos. Saltando creación de datos demo.");
            return;
        }

        List<Departamento> departamentos = Arrays.asList(
            new Departamento("01", "AMAZONAS", true),
            new Departamento("02", "ÁNCASH", true),
            new Departamento("03", "APURIMAC", true),
            new Departamento("04", "AREQUIPA", true),
            new Departamento("05", "AYACUCHO", true),
            new Departamento("06", "CAJAMARCA", true),
            new Departamento("07", "CALLAO", true),
            new Departamento("08", "CUSCO", true),
            new Departamento("09", "HUANCAVELICA", true),
            new Departamento("10", "HUÁNUCO", true),
            new Departamento("11", "ICA", true),
            new Departamento("12", "JUNÍN", true),
            new Departamento("13", "LA LIBERTAD", true),
            new Departamento("14", "LAMBAYEQUE", true),
            new Departamento("15", "LIMA", true),
            new Departamento("16", "LORETO", true),
            new Departamento("17", "MADRE DE DIOS", true),
            new Departamento("18", "MOQUEGUA", true),
            new Departamento("19", "PASCO", true),
            new Departamento("20", "PIURA", true),
            new Departamento("21", "PUNO", true),
            new Departamento("22", "SAN MARTIN", true),
            new Departamento("23", "TACNA", true),
            new Departamento("24", "TUMBES", true),
            new Departamento("25", "UCAYALI", true)
        );

        for (Departamento dep : departamentos) {
            try {
                departamentoRepository.save(dep);
                log.info("✓ Departamento creado: {} - {}", dep.getId(), dep.getNombre());
            } catch (Exception e) {
                log.error("Error al crear departamento {}: {}", dep.getNombre(), e.getMessage());
            }
        }

        log.info("Departamentos de demostración creados exitosamente. Total: {}", departamentos.size());
    }
}
