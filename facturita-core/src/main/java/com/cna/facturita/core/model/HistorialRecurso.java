package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa el historial de acciones sobre recursos en el sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "historial_recursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialRecurso implements Serializable {

    /**
     * Identificador único del historial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** Identificador del usuario que realizó la acción. */
    @Column(name = "usuario_id")
    private Integer idUsuario;

    /** Tipo de recurso afectado. */
    @Column(name = "tipo_recurso")
    private String tipoRecurso;

    /** Identificador del recurso afectado. */
    @Column(name = "recurso_id")
    private Integer idRecurso;

    /** Descripción de la acción realizada. */
    @Column(name = "descripcion")
    private String descripcion;

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
        log.info("Creando HistorialRecurso: usuario_id={}, tipoRecurso='{}', recurso_id={}", idUsuario, tipoRecurso, idRecurso);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando HistorialRecurso: id={}, usuario_id={}", id, idUsuario);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando HistorialRecurso: id={}, usuario_id={}", id, idUsuario);
    }
}