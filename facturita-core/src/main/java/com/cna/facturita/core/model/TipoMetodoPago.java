package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un tipo de método de pago en el sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "tipos_metodos_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMetodoPago implements Serializable {

    /**
     * Identificador único del tipo de método de pago.
     */
    @Id
    @Column(name = "id")
    private String id;

    /** Descripción del tipo de método de pago. */
    @Column(name = "descripcion")
    private String descripcion;

    /** Fecha de creación del registro. */
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    /** Fecha de última actualización del registro. */
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando TipoMetodoPago: id='{}', descripcion='{}'", id, descripcion);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando TipoMetodoPago: id='{}', descripcion='{}'", id, descripcion);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando TipoMetodoPago: id='{}', descripcion='{}'", id, descripcion);
    }
}