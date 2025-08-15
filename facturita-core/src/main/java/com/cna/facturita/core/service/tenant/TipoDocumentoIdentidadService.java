package com.cna.facturita.core.service.tenant;

import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.core.repository.tenant.TipoDocumentoIdentidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoIdentidadService {
    private final TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository;

    public TipoDocumentoIdentidadService(TipoDocumentoIdentidadRepository tipoDocumentoIdentidadRepository) {
        this.tipoDocumentoIdentidadRepository = tipoDocumentoIdentidadRepository;
    }

    public List<TipoDocumentoIdentidad> findAll() {
        return tipoDocumentoIdentidadRepository.findAll();
    }

    public Optional<TipoDocumentoIdentidad> findById(String id) {
        return tipoDocumentoIdentidadRepository.findById(id);
    }

    public TipoDocumentoIdentidad save(TipoDocumentoIdentidad tipoDocumentoIdentidad) {
        return tipoDocumentoIdentidadRepository.save(tipoDocumentoIdentidad);
    }

    public void deleteById(String id) {
        tipoDocumentoIdentidadRepository.deleteById(id);
    }
}
