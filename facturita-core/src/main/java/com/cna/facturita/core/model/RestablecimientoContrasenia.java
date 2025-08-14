package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un restablecimiento de contraseña en el sistema.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "restablecimientos_contrasenias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestablecimientoContrasenia implements Serializable {

    /**
     * Correo electrónico asociado al restablecimiento.
     */
    @Id
    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "token")
    private String token;

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
        log.info("Creando RestablecimientoContrasenia: correoElectronico='{}'", correoElectronico);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando RestablecimientoContrasenia: correoElectronico='{}'", correoElectronico);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando RestablecimientoContrasenia: correoElectronico='{}'", correoElectronico);
    }
}