package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un módulo de la aplicación (por ejemplo, menú o funcionalidad).
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "modulos_app")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuloApp implements Serializable {


    /**
     * Identificador único del módulo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Descripción del módulo.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Slug único del módulo.
     */
    @Column(name = "slug")
    private String slug;

    /**
     * Tipo de módulo.
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * Indica si el módulo está activo.
     */
    @Column(name = "activo")
    private Boolean activo;

    /**
     * Icono asociado al módulo.
     */
    @Column(name = "icono")
    private String icono;

    /**
     * Orden de visualización del módulo.
     */
    @Column(name = "orden")
    private Integer orden;

    /**
     * Identificador del módulo padre (si aplica).
     */
    @Column(name = "id_padre")
    private Integer idPadre;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    /**
     * Fecha de última actualización del registro.
     */
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /**
     * Fecha de eliminación lógica del registro.
     */
    @Column(name = "fecha_de_eliminacion")
    private LocalDateTime fechaDeEliminacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando ModuloApp: descripcion='{}', slug='{}'", descripcion, slug);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando ModuloApp: id={}, descripcion='{}'", id, descripcion);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando ModuloApp: id={}, descripcion='{}'", id, descripcion);
    }
}