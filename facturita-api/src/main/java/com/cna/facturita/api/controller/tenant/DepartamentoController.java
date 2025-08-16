package com.cna.facturita.api.controller.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.core.service.tenant.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public List<Departamento> findAll() {
        return departamentoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> findById(@PathVariable String id) {
        Optional<Departamento> departamento = departamentoService.findById(id);
        return departamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* @PostMapping
    public Departamento save(@RequestBody Departamento departamento) {
        return departamentoService.save(departamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        departamentoService.deleteById(id);
        return ResponseEntity.noContent().build(); 
    }*/
}
