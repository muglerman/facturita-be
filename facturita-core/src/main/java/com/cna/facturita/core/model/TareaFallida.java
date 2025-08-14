package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa una tarea fallida (job) en el sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "tareas_fallidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaFallida implements Serializable {


    /**
     * Identificador único de la tarea fallida.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** UUID de la tarea. */
    @Column(name = "uuid")
    private String uuid;

    /** Nombre de la conexión utilizada. */
    @Column(name = "conexion")
    private String conexion;

    /** Nombre de la cola. */
    @Column(name = "cola")
    private String cola;

    /** Carga útil de la tarea. */
    @Lob
    @Column(name = "carga_util")
    private String cargaUtil;

    /** Excepción asociada a la tarea fallida. */
    @Lob
    @Column(name = "excepcion")
    private String excepcion;

    /** Fecha y hora en que falló la tarea. */
    @Column(name = "fecha_de_fallo")
    private LocalDateTime fechaDeFallo;

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
        log.info("Creando TareaFallida: uuid='{}', cola='{}'", uuid, cola);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando TareaFallida: id={}, uuid='{}'", id, uuid);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando TareaFallida: id={}, uuid='{}'", id, uuid);
    }
}