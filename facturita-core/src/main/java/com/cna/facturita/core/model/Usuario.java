package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un usuario del sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    /** Identificador único del usuario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fecha_verificacion_correo")
    private LocalDateTime fechaVerificacionCorreo;

    @Column(name = "recordar_token")
    private String recordarToken;

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
        log.info("Creando Usuario: nombre='{}', correo='{}'", nombre, email);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando Usuario: id={}, nombre='{}'", id, nombre);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando Usuario: id={}, nombre='{}'", id, nombre);
    }
}