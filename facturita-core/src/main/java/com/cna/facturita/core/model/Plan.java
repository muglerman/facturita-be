package com.cna.facturita.core.model;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa un plan de suscripción en el sistema.
 * Cada plan define los límites, características y costos asociados a la cuenta
 * de un cliente.
 * Esta clase es una entidad de base de datos para la tabla "planes".
 */
@Slf4j
@Entity
@Table(name = "planes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El nombre del plan (Ej: "Básico", "Profesional", "Empresarial").
     */
    private String nombre;

    /**
     * El costo o mensualidad del plan.
     */
    private double precio;

    /**
     * Cantidad máxima de usuarios permitidos en este plan. Si es 0, el número de
     * usuarios es ilimitado.
     */
    @Builder.Default
    @Column(name = "limite_usuarios")
    private int limiteUsuarios = 0;

    /**
     * Cantidad máxima de documentos (facturas, boletas) que se pueden emitir. Si es
     * 0, el número de documentos es ilimitado.
     */
    @Builder.Default
    @Column(name = "limite_documentos")
    private int limiteDocumentos = 0;

    /**
     * Si es verdadero, el límite de documentos también se aplica a las notas de
     * venta.
     */
    @Column(name = "incluir_nota_venta_documentos")
    @Builder.Default
    private boolean incluirNotaVentaDocumentos = false;

    /**
     * Define los tipos de documentos incluidos en el plan (almacenado como JSON).
     * Ejemplo: {"invoice": true, "receipt": true, "credit_note": false}
     */
    @Type(JsonType.class)
    @Column(name = "documentos_plan", columnDefinition = "json")
    private List<String> documentosPlan;

    /**
     * Si es verdadero, el plan no puede ser modificado o asignado.
     */
    @Builder.Default
    private boolean habilitado = false;

    /**
     * Si es verdadero, se incluye a las notas de venta dentro del límite monetario.
     */
    @Column(name = "incluir_nota_venta_ventas")
    @Builder.Default
    private boolean incluirNotaVentaVentas = false;

    /**
     * Cantidad máxima de ventas permitidos en este plan. Si es 0, el monto de
     * ventas es ilimitado.
     */
    @Column(name = "limite_ventas")
    @Builder.Default
    private BigDecimal limiteVentas = BigDecimal.ZERO;

    /**
     * Cantidad máxima de establecimientos permitidos en este plan. Si es 0, el
     * número de establecimientos es ilimitado, ignorando
     * 'limiteEstablecimientos'.
     */
    @Column(name = "limite_establecimientos")
    @Builder.Default
    private int limiteEstablecimientos = 0;

    /**
     * Fecha y hora de creación del registro. Se establece automáticamente al
     * persistir.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /**
     * Fecha y hora de la última actualización del registro. Se actualiza
     * automáticamente.
     */
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Método de callback de JPA que se ejecuta antes de que la entidad sea
     * persistida.
     * Establece las fechas de creación/actualización y registra el evento.
     */
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        log.info("[CORE] -> [Model] -> [Plan] -> [onCreate]: Creando nuevo plan con nombre: '{}', fechaCreacion: '{}'",
                this.nombre, fechaCreacion);
    }

    /**
     * Método de callback de JPA que se ejecuta antes de que la entidad sea
     * actualizada.
     * Actualiza la fecha de modificación y registra el evento.
     */
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
        log.info(
                "[CORE] -> [Model] -> [Plan] -> [onUpdate]: Actualizando plan ID: {}, Nombre: '{}', fechaActualizacion: '{}'",
                this.id, this.nombre, fechaActualizacion);
    }

    @PreRemove
    protected void onDelete() {
        log.info(
                "[CORE] -> [Model] -> [Plan] -> [onDelete]: Eliminando plan ID: {}, Nombre: '{}', fechaEliminacion: '{}'",
                this.id, this.nombre, LocalDateTime.now());
    }
}