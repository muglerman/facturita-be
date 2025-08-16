package com.cna.facturita.core.loader.tenant;

import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.core.repository.tenant.TipoDocumentoIdentidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(7)
public class TipoDocumentoDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TipoDocumentoDataLoader.class);

    private final TipoDocumentoIdentidadRepository tipoDocumentoRepository;

    public TipoDocumentoDataLoader(TipoDocumentoIdentidadRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("=== Iniciando carga de datos demo para Tipos de Documento ===");

        if (tipoDocumentoRepository.count() > 0) {
            log.info("Ya existen tipos de documento en la base de datos. Saltando creación de datos demo.");
            return;
        }

        Object[][] data = {
            {"0", true, "DOC.TRIB.NO.DOM.SIN.RUC"},
            {"1", true, "DNI"},
            {"4", true, "CE"},
            {"6", true, "RUC"},
            {"7", true, "PASAPORTE"},
            {"A", false, "CED. DIPLOMÁTICA DE IDENTIDAD"},
            {"B", false, "DOCUMENTO IDENTIDAD PAÍS RESIDENCIA-NO.D"},
            {"C", false, "TAX IDENTIFICATION NUMBER - TIN – DOC TRIB PP.NN"},
            {"D", false, "IDENTIFICATION NUMBER - IN – DOC TRIB PP. JJ"},
            {"E", false, "TAM- TARJETA ANDINA DE MIGRACIÓN"}
        };

        for (Object[] row : data) {
            TipoDocumentoIdentidad tipo = TipoDocumentoIdentidad.builder()
                .id(row[0].toString())
                .estado((Boolean) row[1])
                .nombre(row[2].toString().toUpperCase())
                .build();
            tipoDocumentoRepository.save(tipo);
            log.info("✓ TipoDocumento creado: {} - {} - {}", tipo.getId(), tipo.isEstado(), tipo.getNombre());
        }

        log.info("Tipos de documento de demostración creados exitosamente. Total: {}", data.length);
    }
}
