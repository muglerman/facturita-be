package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa la relación entre un plan y un tipo de documento.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "planes_documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDocumento implements Serializable {

    /**
     * Identificador único de la relación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_plan")
    private Integer idPlan;

    @Column(name = "id_tipo_documento")
    private String idTipoDocumento;

    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando PlanDocumento: idPlan={}, idTipoDocumento={}", idPlan, idTipoDocumento);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando PlanDocumento: id={}, idPlan={}, idTipoDocumento={}", id, idPlan, idTipoDocumento);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando PlanDocumento: id={}, idPlan={}, idTipoDocumento={}", id, idPlan, idTipoDocumento);
    }
}