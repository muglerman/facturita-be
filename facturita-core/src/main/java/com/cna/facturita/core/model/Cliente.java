package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela un cliente en el sistema. Centraliza información de contacto,
 * configuración, estado de bloqueo y el plan de suscripción.
 * Incluye trazabilidad y logs en los eventos de ciclo de vida.
 */
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private static final Logger log = LoggerFactory.getLogger(Cliente.class);

    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Hostname que identifica al cliente en una arquitectura multi-tenant.
     */
    @ManyToOne
    @JoinColumn(name = "hostname_id")
    private Hostname hostname;

    /**
     * Número de documento de identidad (RUC, DNI, etc.).
     */
    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento;

    /**
     * Nombre completo o razón social del cliente.
     */
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    /**
     * Correo electrónico principal del cliente.
     */
    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    /**
     * Token de API para integraciones.
     */
    @Column(name = "token_api", nullable = false, unique = true)
    private String tokenApi;

    // --- Banderas de Estado y Bloqueo ---

    /** Indica si el cliente está bloqueado. */
    @Column(name = "esta_bloqueado")
    private boolean estaBloqueado = false;

    /** Indica si los usuarios del cliente están bloqueados. */
    @Column(name = "usuarios_bloqueados")
    private boolean usuariosBloqueados = false;

    /** Indica si el inquilino está bloqueado. */
    @Column(name = "inquilino_bloqueado")
    private boolean inquilinoBloqueado = false;

    /** Indica si la emisión está bloqueada. */
    @Column(name = "emision_bloqueada")
    private boolean emisionBloqueada = false;

    /** Indica si se restringe por límite de ventas. */
    @Column(name = "restringir_por_limite_ventas")
    private boolean restringirPorLimiteVentas = false;

    /** Indica si la creación de establecimientos está bloqueada. */
    @Column(name = "creacion_establecimientos_bloqueada")
    private boolean creacionEstablecimientosBloqueada = false;

    /**
     * Plan de suscripción al que pertenece el cliente.
     */
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    // --- Configuraciones Adicionales ---

    /** Indica si está habilitada la lista de productos. */
    @Column(name = "habilitar_lista_productos")
    private boolean habilitarListaProductos = true;

    // --- Configuración de Correo (SMTP) ---

    /** Tipo de cifrado SMTP. */
    private String smtpEncryption;

    /** Contraseña SMTP. */
    private String smtpPassword;

    /** Usuario SMTP. */
    private String smtpUser;

    /** Puerto SMTP. */
    private Integer smtpPort = 0;

    /** Host SMTP. */
    private String smtpHost;

    /**
     * Fecha de inicio para el ciclo de facturación.
     */
    private LocalDate inicioCicloFacturacion;

    // --- Campos de Auditoría ---

    /** Fecha de creación del registro. */
    private LocalDateTime fechaDeCreacion;

    /** Fecha de última actualización del registro. */
    private LocalDateTime fechaDeActualizacion;

    /** Fecha de eliminación lógica del registro. */
    private LocalDateTime fechaDeEliminacion;

    /**
     * Lista de pagos realizados por el cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PagoCliente> pagos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        log.info("Creando nuevo cliente con documento: '{}' y razón social: '{}'", this.numeroDocumento, this.razonSocial);
    }

    @PreUpdate
    protected void onUpdate() {
        log.info("Actualizando cliente ID: {}, documento: '{}', razón social: '{}'", this.id, this.numeroDocumento, this.razonSocial);
    }

    @PreRemove
    protected void onDelete() {
        log.info("Eliminando cliente ID: {}, documento: '{}', razón social: '{}'", this.id, this.numeroDocumento, this.razonSocial);
    }
}