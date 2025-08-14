package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Entidad que representa un Hostname asociado a un sitio web.
 * Incluye campos de trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "hostnames")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hostname {

    /**
     * Identificador único del Hostname.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de dominio completo (FQDN).
     */
    @Column(name = "fqdn", nullable = false, unique = true)
    private String fqdn;

    /**
     * Destino de redirección, si aplica.
     */
    @Column(name = "redirigir_a")
    private String redirigirA;

    /**
     * Indica si se debe forzar HTTPS.
     */
    @Column(name = "forzar_https")
    private boolean forzarHttps = false;

    /**
     * Fecha desde la que está en mantenimiento.
     */
    @Column(name = "en_mantenimiento_desde")
    private LocalDateTime enMantenimientoDesde;

    /**
     * Sitio web asociado.
     */
    @ManyToOne
    @JoinColumn(name = "sitioweb_id")
    private SitioWeb sitioWeb;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    /**
     * Fecha de última actualización.
     */
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechaDeActualizacion;

    /**
     * Fecha de eliminación lógica.
     */
    @Column(name = "fecha_de_eliminacion")
    private LocalDateTime fechaDeEliminacion;

    /**
     * Asigna la fecha de creación y registra el evento.
     */
    @PrePersist
    public void prePersist() {
        fechaDeCreacion = LocalDateTime.now();
        log.info("Creando Hostname: fqdn={}, sitioWeb={}", fqdn, sitioWeb != null ? sitioWeb.getId() : null);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando Hostname: id={}, fqdn={}", id, fqdn);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        fechaDeEliminacion = LocalDateTime.now();
        log.info("Eliminando Hostname: id={}, fqdn={}", id, fqdn);
    }
}