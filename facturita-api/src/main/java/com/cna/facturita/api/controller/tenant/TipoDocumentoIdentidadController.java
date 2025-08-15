package com.cna.facturita.api.controller.tenant;

import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.core.service.tenant.TipoDocumentoIdentidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenant/tipo-documento-identidad")
public class TipoDocumentoIdentidadController {
    private final TipoDocumentoIdentidadService tipoDocumentoIdentidadService;

    public TipoDocumentoIdentidadController(TipoDocumentoIdentidadService tipoDocumentoIdentidadService) {
        this.tipoDocumentoIdentidadService = tipoDocumentoIdentidadService;
    }

    @GetMapping
    public List<TipoDocumentoIdentidad> findAll() {
        return tipoDocumentoIdentidadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoIdentidad> findById(@PathVariable String id) {
        Optional<TipoDocumentoIdentidad> tipo = tipoDocumentoIdentidadService.findById(id);
        return tipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoDocumentoIdentidad save(@RequestBody TipoDocumentoIdentidad tipo) {
        return tipoDocumentoIdentidadService.save(tipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        tipoDocumentoIdentidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
