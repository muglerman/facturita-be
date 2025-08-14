package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa una migración ejecutada en el sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */

@Slf4j
@Entity
@Table(name = "migraciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Migracion implements Serializable {

    /**
     * Nombre de la migración.
     */
    @Id
    @Column(name = "migracion")
    private String migracion;

    /** Número de lote de la migración. */
    @Column(name = "lote")
    private Integer lote;

    /** Fecha de creación del registro. */
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    /** Fecha de última actualización del registro. */
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /** Fecha de eliminación lógica del registro. */
    @Column(name = "fecha_de_eliminacion")
    private LocalDateTime fechaDeEliminacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando Migracion: migracion='{}', lote={}", migracion, lote);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando Migracion: migracion='{}', lote={}", migracion, lote);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando Migracion: migracion='{}', lote={}", migracion, lote);
    }
}