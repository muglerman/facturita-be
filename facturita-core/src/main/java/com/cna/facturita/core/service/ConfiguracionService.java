package com.cna.facturita.core.service;

import com.cna.facturita.core.model.Configuracion;
import com.cna.facturita.core.repository.ConfiguracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfiguracionService {

    private final ConfiguracionRepository configuracionRepository;

    @Autowired
    public ConfiguracionService(ConfiguracionRepository configuracionRepository) {
        this.configuracionRepository = configuracionRepository;
    }


    /**
     * Obtiene la única configuración registrada en el sistema.
     * @return Configuracion única o null si no existe
     */
    public Configuracion getConfiguracionUnica() {
        return configuracionRepository.findTopByOrderByIdAsc();
    }

    public Optional<Configuracion> findById(Integer id) {
        return configuracionRepository.findById(id);
    }

    public Configuracion save(Configuracion configuracion) {
        return configuracionRepository.save(configuracion);
    }

    public void deleteById(Integer id) {
        configuracionRepository.deleteById(id);
    }
}
