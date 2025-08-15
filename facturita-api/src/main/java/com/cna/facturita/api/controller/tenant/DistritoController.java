package com.cna.facturita.api.controller.tenant;

import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.service.tenant.DistritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distritos")
public class DistritoController {
    private final DistritoService distritoService;

    public DistritoController(DistritoService distritoService) {
        this.distritoService = distritoService;
    }

    @GetMapping
    public List<Distrito> findAll() {
        return distritoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distrito> findById(@PathVariable String id) {
        Optional<Distrito> distrito = distritoService.findById(id);
        return distrito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Distrito save(@RequestBody Distrito distrito) {
        return distritoService.save(distrito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        distritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
