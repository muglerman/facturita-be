package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa una configuración del sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Entity
@Table(name = "configuraciones_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class conf_user implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Configuracion.class);

    /**
     * Identificador único de la configuración.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ambito")
    /** Ámbito de la configuración (por ejemplo, global, cliente, etc.). */
    private String ambito;

    @Column(name = "grupo")
    /** Grupo al que pertenece la configuración. */
    private String grupo;

    @Column(name = "nombre")
    /** Nombre de la configuración. */
    private String nombre;

    @Column(name = "valor")
    /** Valor asignado a la configuración. */
    private String valor;

    @Column(name = "descripcion")
    /** Descripción de la configuración. */
    private String descripcion;

    @Column(name = "activo")
    /** Indica si la configuración está activa. */
    private Boolean activo;

    @Column(name = "fecha_creacion")
    /** Fecha de creación del registro. */
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    /** Fecha de última actualización del registro. */
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_eliminacion")
    /** Fecha de eliminación lógica del registro. */
    private LocalDateTime fechaEliminacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
        log.info("Creando Configuracion: nombre='{}', grupo='{}'", nombre, grupo);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
        log.info("Actualizando Configuracion: id={}, nombre='{}'", id, nombre);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaEliminacion = LocalDateTime.now();
        log.info("Eliminando Configuracion: id={}, nombre='{}'", id, nombre);
    }
}