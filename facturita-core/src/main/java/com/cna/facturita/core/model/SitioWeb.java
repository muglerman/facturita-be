package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Representa un sitio web en el sistema.
 * Actúa como el contenedor principal para configuraciones y dominios.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "sitiosweb") // Original: websites
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SitioWeb {

    /**
     * Identificador único del sitio web.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador Único Universal (UUID) para referenciar el sitio de forma externa y segura.
     */
    @Column(name = "uuid")
    private String uuid;

    /**
     * Fecha y hora de creación del registro.
     */
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    /**
     * Fecha y hora de la última actualización del registro.
     */
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /**
     * Fecha en que se marcó como eliminado (borrado lógico).
     */
    @Column(name = "fecha_de_eliminacion")
    private LocalDateTime fechaDeEliminacion;

    /**
     * Nombre de la configuración de conexión a la base de datos asociada.
     */
    @Column(name = "conexion_de_base_de_datos")
    private String conexionDeBaseDeDatos;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando SitioWeb: uuid={}", uuid);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando SitioWeb: id={}, uuid={}", id, uuid);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando SitioWeb: id={}, uuid={}", id, uuid);
    }
}