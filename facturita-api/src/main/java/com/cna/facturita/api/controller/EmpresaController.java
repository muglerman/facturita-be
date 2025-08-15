package com.cna.facturita.api.controller;

import com.cna.facturita.api.service.EmpresaService;
import com.cna.facturita.core.model.Empresa;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> getEmpresas() {
        return empresaService.listarEmpresas();
    }
}