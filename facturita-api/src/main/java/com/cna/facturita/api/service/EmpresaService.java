package com.cna.facturita.api.service;

import com.cna.facturita.core.model.Empresa;
import com.cna.facturita.core.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class EmpresaService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpresaService.class);

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> listarEmpresas() {
    	logger.info("[API] -> [Service] -> [Empresa] -> Listando todas las empresas");
        return empresaRepository.findAll();
    }
}