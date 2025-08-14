package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

/**
 * Representa una marca de tarjeta (por ejemplo, Visa, MasterCard).
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "marcas_tarjeta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaTarjeta {

    /**
     * Identificador único de la marca de tarjeta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Descripción de la marca de tarjeta.
     */
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

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
        log.info("Creando MarcaTarjeta: descripcion='{}'", descripcion);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando MarcaTarjeta: id={}, descripcion='{}'", id, descripcion);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando MarcaTarjeta: id={}, descripcion='{}'", id, descripcion);
    }
}