package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa la relación entre un socio y un restaurante.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "socios_restaurantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioRestaurante implements Serializable {

    /**
     * Identificador único del socio-restaurante.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    /** Identificador externo. */
    @Column(name = "external_id")
    private String idExterno;

    /** Indica si el socio está activo. */
    @Column(name = "activo")
    private Boolean activo;

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
        log.info("Creando SocioRestaurante: nombre='{}', idExterno='{}'", nombre, idExterno);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando SocioRestaurante: id={}, nombre='{}'", id, nombre);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando SocioRestaurante: id={}, nombre='{}'", id, nombre);
    }
}