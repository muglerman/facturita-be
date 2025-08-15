package com.cna.facturita.dto;

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
    @JsonProperty("admin_bloqueado") Boolean adminBloqueado, ///< Indica si el acceso de administrador está bloqueado
    @JsonProperty("certificado") String certificado, ///< Certificado digital para firma
    @JsonProperty("envio_soap_id") String envioSoapId, ///< Identificador de envío SOAP
    @JsonProperty("tipo_soap_id") String tipoSoapId, ///< Tipo de servicio SOAP
    @JsonProperty("soap_username") String soapUsername, ///< Usuario SOAP
    @JsonProperty("soap_password") String soapPassword, ///< Contraseña SOAP
    @JsonProperty("soap_url") String soapUrl, ///< URL del endpoint SOAP
    @JsonProperty("token_public_culqui") String tokenPublicCulqui, ///< Token público Culqui
    @JsonProperty("token_private_culqui") String tokenPrivateCulqui, ///< Token privado Culqui
    @JsonProperty("url_apiruc") String urlApiRuc, ///< URL de consulta RUC
    @JsonProperty("token_apiruc") String tokenApiRuc, ///< Token de consulta RUC
    @JsonProperty("use_login_global") Boolean useLoginGlobal, ///< Login global habilitado
    @JsonProperty("login") String login, ///< Usuario de login global
    @JsonProperty("whatsapp_habilitado") Boolean whatsappHabilitado, ///< WhatsApp habilitado
    @JsonProperty("regex_password_cliente") Boolean regexPasswordCliente ///< Validación regex en contraseña cliente
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
        return ConfiguracionDTO.builder()
                .id(config.getId())
                .adminBloqueado(config.getAdminBloqueado())
                .certificado(config.getCertificado())
                .envioSoapId(config.getEnvioSoapId())
                .tipoSoapId(config.getTipoSoapId())
                .soapUsername(config.getSoapUsername())
                .soapPassword(config.getSoapPassword())
                .soapUrl(config.getSoapUrl())
                .tokenPublicCulqui(config.getTokenPublicCulqui())
                .tokenPrivateCulqui(config.getTokenPrivateCulqui())
                .urlApiRuc(config.getUrlApiRuc())
                .tokenApiRuc(config.getTokenApiRuc())
                .useLoginGlobal(config.getUseLoginGlobal())
                .login(config.getLogin())
                .whatsappHabilitado(config.getWhatsappHabilitado())
                .regexPasswordCliente(config.getRegexPasswordCliente())
                .build();
    }
}
