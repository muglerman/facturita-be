package com.cna.facturita.core.service.tenant;

import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.repository.tenant.DistritoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DistritoService {
    private final DistritoRepository distritoRepository;

    public DistritoService(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    public List<Distrito> findAll() {
        return distritoRepository.findAll();
    }

    public Optional<Distrito> findById(String id) {
        return distritoRepository.findById(id);
    }

    public Distrito save(Distrito distrito) {
        return distritoRepository.save(distrito);
    }

    public void deleteById(String id) {
        distritoRepository.deleteById(id);
    }
}
