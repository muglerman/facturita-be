package com.cna.facturita.core.repository;

import com.cna.facturita.core.model.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {
    /**
     * Obtiene la primera configuración registrada en la base de datos.
     * @return Configuracion única o null si no existe
     */
    Configuracion findTopByOrderByIdAsc();
}
