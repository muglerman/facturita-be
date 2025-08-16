package com.cna.facturita.api.controller.tenant;

import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.core.service.tenant.ProvinciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;

    public ProvinciaController(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @GetMapping
    public List<Provincia> findAll() {
        return provinciaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provincia> findById(@PathVariable String id) {
        Optional<Provincia> provincia = provinciaService.findById(id);
        return provincia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* @PostMapping
    public Provincia save(@RequestBody Provincia provincia) {
        return provinciaService.save(provincia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        provinciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    } */
}
