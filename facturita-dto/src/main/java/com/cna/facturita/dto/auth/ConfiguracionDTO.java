package com.cna.facturita.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.cna.facturita.core.model.Configuracion;
import lombok.Builder;

@Builder
public record ConfiguracionDTO(
    /**
     * DTO para la transferencia de datos de configuración entre capas.
     * <p>
     * Este record representa la estructura serializada de una configuración, utilizada en la API REST y en la capa de servicios.
     * Todos los campos están documentados para facilitar el mantenimiento y la integración.
     * <p>
     * <b>Ejemplo de uso:</b>
     * <pre>
     * ConfiguracionDTO dto = ConfiguracionDTO.fromEntity(configuracion);
     * </pre>
     *
     * @author Equipo Facturita
     * @since 2025
     */
    @JsonProperty("id") Integer id, ///< Identificador único de la configuración
    @JsonProperty("locked_admin") Boolean adminBloqueado, ///< Indica si el acceso de administrador está bloqueado
    @JsonProperty("certificate") String certificado, ///< Certificado digital para firma
    @JsonProperty("soap_send_id") String envioSoapId, ///< Identificador de envío SOAP
    @JsonProperty("soap_type_id") String soapTypeId, ///< Tipo de servicio SOAP
    @JsonProperty("soap_username") String soapUsername, ///< Usuario SOAP
    @JsonProperty("soap_password") String soapPassword, ///< Contraseña SOAP
    @JsonProperty("soap_url") String soapUrl, ///< URL del endpoint SOAP
    @JsonProperty("token_public_culqui") String tokenPublicCulqui, ///< Token público Culqui
    @JsonProperty("token_private_culqui") String tokenPrivateCulqui, ///< Token privado Culqui
    @JsonProperty("url_apiruc") String urlApiRuc, ///< URL de consulta RUC
    @JsonProperty("token_apiruc") String tokenApiRuc, ///< Token de consulta RUC
    @JsonProperty("use_login_global") Boolean useLoginGlobal, ///< Login global habilitado
    @JsonProperty("login") String login, ///< Usuario de login global
    @JsonProperty("enable_whatsapp") Boolean habilitarWhatsapp, ///< WhatsApp habilitado
    @JsonProperty("regex_password_client") Boolean regexPasswordClient ///< Validación regex en contraseña cliente
) {
    /**
     * Convierte una entidad {@link Configuracion} en un DTO serializable para la API.
     * <p>
     * Útil para exponer datos de configuración en endpoints REST y desacoplar la capa de persistencia.
     *
     * @param config Entidad JPA de configuración
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static ConfiguracionDTO fromEntity(Configuracion config) {
        return new ConfiguracionDTO(
            config.getId(),
            config.getAdminBloqueado(),
            config.getCertificado(),
            config.getEnvioSoapId(),
            config.getSoapTypeId(),
            config.getSoapUsername(),
            config.getSoapPassword(),
            config.getSoapUrl(),
            config.getTokenPublicCulqui(),
            config.getTokenPrivateCulqui(),
            config.getUrlApiRuc(),
            config.getTokenApiRuc(),
            config.getUseLoginGlobal(),
            config.getLogin(),
            config.getHabilitarWhatsapp(),
            config.getRegexPasswordClient()
        );
    }
}
