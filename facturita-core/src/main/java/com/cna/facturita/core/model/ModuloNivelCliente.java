package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa la relación entre un módulo, un nivel de módulo y un cliente.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "niveles_modulo_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuloNivelCliente implements Serializable {

    /**
     * Identificador único de la relación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_modulo")
    private Integer idModulo;

    @Column(name = "id_nivel_modulo")
    private Integer idNivelModulo;

    @Column(name = "id_cliente")
    private Integer idCliente;

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
        log.info("Creando ModuloNivelCliente: idModulo={}, idNivelModulo={}, idCliente={}", idModulo, idNivelModulo, idCliente);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando ModuloNivelCliente: id={}, idModulo={}, idNivelModulo={}, idCliente={}", id, idModulo, idNivelModulo, idCliente);
    }

    /**
     * Asigna la fecha de eliminación y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando ModuloNivelCliente: id={}, idModulo={}, idNivelModulo={}, idCliente={}", id, idModulo, idNivelModulo, idCliente);
    }
}