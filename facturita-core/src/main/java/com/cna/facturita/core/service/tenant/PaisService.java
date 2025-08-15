package com.cna.facturita.core.service.tenant;

import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.core.repository.tenant.PaisRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService {
    private final PaisRepository paisRepository;

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public List<Pais> findAll() {
        return paisRepository.findAll();
    }

    public Optional<Pais> findById(String id) {
        return paisRepository.findById(id);
    }

    public Pais save(Pais pais) {
        return paisRepository.save(pais);
    }

    public void deleteById(String id) {
        paisRepository.deleteById(id);
    }
}
