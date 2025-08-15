package com.cna.facturita.dto.form;

import lombok.Getter;
import java.time.LocalDateTime;

import com.cna.facturita.dto.validation.ConditionalConfiguracionValidation;

/**
 * DTO para la recepción y validación de datos de configuración desde la capa de presentación.
 * <p>
 * Incluye anotaciones de validación condicional y logs para trazabilidad de cambios y auditoría.
 * <p>
 * <b>Uso recomendado:</b> Utilizar este formulario en endpoints REST para crear o actualizar configuraciones.
 * <p>
 * <b>Ejemplo:</b>
 * <pre>
 * ConfiguracionForm form = new ConfiguracionForm();
 * form.setAdminBloqueado(true);
 * form.setCertificado("certificado.pem");
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Getter
@ConditionalConfiguracionValidation
public class ConfiguracionForm {
    /** Identificador único de la configuración (solo para edición). */
    private Integer id;
    /** Indica si el acceso de administrador está bloqueado para el tenant. */
    private Boolean adminBloqueado;

    /** Nombre de la configuración. No puede estar vacío. */
    @jakarta.validation.constraints.NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /** Valor de la configuración. No puede estar vacío. */
    @jakarta.validation.constraints.NotBlank(message = "El valor es obligatorio")
    private String valor;
    /** Ruta o contenido del certificado digital. */
    private String certificado;
    /** Identificador del tipo de envío SOAP. */
    private String envioSoapId;
    /** Identificador del tipo de servicio SOAP. */
    private String soapTypeId;
    /** Usuario para autenticación en el servicio SOAP. */
    private String soapUsername;
    /** Contraseña para autenticación en el servicio SOAP. */
    private String soapPassword;
    /** URL del endpoint SOAP. */
    private String soapUrl;
    /** Token público para integración con Culqui. */
    private String tokenPublicCulqui;
    /** Token privado para integración con Culqui. */
    private String tokenPrivateCulqui;
    /** URL del servicio externo para consulta de RUC. */
    private String urlApiRuc;
    /** Token de autenticación para el servicio de consulta de RUC. */
    private String tokenApiRuc;
    /** Indica si se debe usar el login global. */
    private Boolean useLoginGlobal;
    /** Usuario de login global. */
    private String login;
    /** Indica si está habilitada la integración con WhatsApp. */
    private Boolean habilitarWhatsapp;
    /** Indica si se debe aplicar validación regex a la contraseña del cliente. */
    private Boolean regexPasswordClient;
    /** Fecha de última actualización del registro. */
    private LocalDateTime fechaActualizacion;

    /**
     * Log de auditoría para cambios en el formulario.
     * Llamar este método después de modificar cualquier campo relevante.
     */
    public void logChange(String campo, Object valor) {
        org.slf4j.LoggerFactory.getLogger(ConfiguracionForm.class)
            .info("[FORM AUDIT] Cambio en '{}': {}", campo, valor);
    }

    /**
     * Ejemplo de método que podría usarse para validar el formulario antes de persistir.
     */
    public boolean isValid() {
        //boolean valido = certificado != null && soapUsername != null;
        //org.slf4j.LoggerFactory.getLogger(ConfiguracionForm.class)
        //    .info("[FORM VALIDATION] Validación ejecutada: {}", valido ? "OK" : "ERROR");
        //return valido;
        return true;
    }
}