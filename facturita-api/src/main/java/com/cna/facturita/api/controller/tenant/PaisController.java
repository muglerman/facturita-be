package com.cna.facturita.api.controller.tenant;

import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.core.service.tenant.PaisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public List<Pais> findAll() {
        return paisService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> findById(@PathVariable String id) {
        Optional<Pais> pais = paisService.findById(id);
        return pais.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pais save(@RequestBody Pais pais) {
        return paisService.save(pais);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        paisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
