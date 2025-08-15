package com.cna.facturita.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa la configuración global y por tenant del sistema Facturita.
 * <p>
 * Esta clase modela todos los parámetros configurables del sistema, incluyendo credenciales,
 * endpoints, tokens, flags de seguridad y opciones de integración. Cada campo está mapeado
 * directamente a la tabla <b>configurations</b> en la base de datos, siguiendo el esquema real.
 * <p>
 * <b>Buenas prácticas:</b>
 * <ul>
 *   <li>Utiliza el patrón Builder para instanciación segura y legible.</li>
 *   <li>Incluye logs en eventos de ciclo de vida para trazabilidad y auditoría.</li>
 *   <li>Todos los campos están documentados para facilitar el mantenimiento y onboarding.</li>
 * </ul>
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * Configuracion config = Configuracion.builder()
 *     .adminBloqueado(false)
 *     .certificado("certificado.pem")
 *     .soapUsername("usuario")
 *     .build();
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Entity
@Table(name = "configuraciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuracion implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Configuracion.class);

    /**
     * Identificador único de la configuración.
     * Autoincremental en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Indica si el acceso de administrador está bloqueado para el tenant.
     */
    @Column(name = "admin_bloqueado", nullable = false)
    private Boolean adminBloqueado;

    /**
     * Ruta o contenido del certificado digital utilizado para la firma de documentos.
     */
    @Column(name = "certificado", length = 255)
    private String certificado;

    /**
     * Identificador del tipo de envío SOAP (por ejemplo, "01" para producción).
     */
    @Column(name = "envio_soap_id", length = 2)
    private String envioSoapId;

    /**
     * Identificador del tipo de servicio SOAP (por ejemplo, "01" para SUNAT).
     */
    @Column(name = "tipo_soap_id", length = 2)
    private String tipoSoapId;

    /**
     * Usuario para autenticación en el servicio SOAP.
     */
    @Column(name = "soap_username", length = 255)
    private String soapUsername;

    /**
     * Contraseña para autenticación en el servicio SOAP.
     */
    @Column(name = "soap_password", length = 255)
    private String soapPassword;

    /**
     * URL del endpoint SOAP para el envío de comprobantes.
     */
    @Column(name = "soap_url", length = 255)
    private String soapUrl;

    /**
     * Token público para integración con Culqui (pagos).
     */
    @Column(name = "token_public_culqui", columnDefinition = "text")
    private String tokenPublicCulqui;

    /**
     * Token privado para integración con Culqui (pagos).
     */
    @Column(name = "token_private_culqui", columnDefinition = "text")
    private String tokenPrivateCulqui;

    /**
     * URL del servicio externo para consulta de RUC.
     */
    @Column(name = "url_apiruc", columnDefinition = "text")
    private String urlApiRuc;

    /**
     * Token de autenticación para el servicio de consulta de RUC.
     */
    @Column(name = "token_apiruc", columnDefinition = "text")
    private String tokenApiRuc;

    /**
     * Indica si se debe usar el login global para todos los tenants.
     */
    @Column(name = "use_login_global", nullable = false)
    private Boolean useLoginGlobal;

    /**
     * Usuario de login global (si aplica).
     */
    @Column(name = "login", columnDefinition = "text")
    private String login;

    // @Column(name = "apk_url", columnDefinition = "text")
    // private String apkUrl;

    /**
     * Indica si está habilitada la integración con WhatsApp para notificaciones.
     */
    @Column(name = "whatsapp_habilitado")
    private Boolean whatsappHabilitado;

    /**
     * Indica si se debe aplicar validación regex a la contraseña del cliente.
     */
    @Column(name = "regex_password_cliente", nullable = false)
    private Boolean regexPasswordCliente;

    /**
     * Fecha de creación del registro de configuración.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /**
     * Fecha de última actualización del registro de configuración.
     */
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;


    // @Column(name = "tenant_show_ads", nullable = false)
    // private Boolean tenantShowAds;

    // @Column(name = "tenant_image_ads", length = 255)
    // private String tenantImageAds;

    /**
     * Evento JPA que se ejecuta antes de actualizar la entidad.
     * Actualiza el campo de fecha de modificación y registra el evento en el log para auditoría.
     */
    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
        LoggerFactory.getLogger(Configuracion.class)
            .info("[AUDIT] Actualizando Configuracion: id={}, fechaActualizacion={}", id, fechaActualizacion);
    }
}