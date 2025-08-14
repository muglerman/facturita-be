package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa un pago realizado por un cliente.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Slf4j
@Entity
@Table(name = "pagos_clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoCliente implements Serializable {

    /** Identificador único del pago. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** Cliente que realiza el pago. */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /** Fecha en que se realizó el pago. */
    @Column(name = "fecha_de_pago", nullable = false)
    private LocalDate fechaDePago;

    /** Tipo de método de pago utilizado. */
    @ManyToOne
    @JoinColumn(name = "tipo_metodo_pago_id", nullable = false)
    private TipoMetodoPago tipoMetodoPago;

    /** Indica si el pago fue realizado con tarjeta. */
    @Column(name = "tiene_tarjeta")
    private boolean tieneTarjeta = false;

    /** Marca de la tarjeta utilizada, si aplica. */
    @ManyToOne
    @JoinColumn(name = "marca_tarjeta_id")
    private MarcaTarjeta marcaTarjeta;

    /** Referencia del pago. */
    @Column(name = "referencia")
    private String referencia;

    /** Monto pagado. */
    @Column(name = "pago", nullable = false, precision = 12, scale = 2)
    private BigDecimal pago;

    /** Estado del pago. */
    @Column(name = "estado")
    private boolean estado = false;

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
        log.info("Creando PagoCliente: cliente={}, pago={}, fechaDePago={}",
                cliente != null ? cliente.getId() : null, pago, fechaDePago);
    }

    /**
     * Asigna la fecha de actualización y registra el evento.
     */
    @PreUpdate
    public void preUpdate() {
        fechaDeActualizacion = LocalDateTime.now();
        log.info("Actualizando PagoCliente: id={}, cliente={}",
                id, cliente != null ? cliente.getId() : null);
    }

    /**
     * Asigna la fecha de eliminación lógica y registra el evento.
     */
    @PreRemove
    public void preRemove() {
        log.info("Eliminando PagoCliente: id={}, cliente={}", id, cliente != null ? cliente.getId() : null);
    }
}