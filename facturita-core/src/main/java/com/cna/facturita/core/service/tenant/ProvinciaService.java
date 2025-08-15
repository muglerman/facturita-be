package com.cna.facturita.core.service.tenant;

import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.core.repository.tenant.ProvinciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    public Optional<Provincia> findById(String id) {
        return provinciaRepository.findById(id);
    }

    public Provincia save(Provincia provincia) {
        return provinciaRepository.save(provincia);
    }

    public void deleteById(String id) {
        provinciaRepository.deleteById(id);
    }
}
