
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Cliente;
import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de cliente entre capas.
 */
@Builder
public record ClienteDTO(
    @JsonProperty("id") Integer id,
    @JsonProperty("tipo_documento_identidad") @NotNull(message = "El tipo de documento es obligatorio") TipoDocumentoIdentidad tipoDocumentoIdentidad,
    @JsonProperty("numero_documento") @NotBlank(message = "El número de documento es obligatorio") String numeroDocumento,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("direccion") String direccion,
    @JsonProperty("distrito") Distrito distrito,
    @JsonProperty("pais") Pais pais,
    @JsonProperty("email") String email,
    @JsonProperty("telefono") String telefono,
    @JsonProperty("condicion_sunat") String condicionSunat,
    @JsonProperty("estado_sunat") String estadoSunat,
    @JsonProperty("estado") boolean estado,
    @JsonProperty("fecha_actualizacion") LocalDateTime fechaActualizacion
) {

    /**
     * Convierte una entidad {@link Cliente} en un DTO serializable para la API.
     * @param cliente Entidad JPA de cliente
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static ClienteDTO fromEntity(Cliente cliente) {
        return ClienteDTO.builder()
            .id(cliente.getId())
            .tipoDocumentoIdentidad(cliente.getTipoDocumentoIdentidad())
            .numeroDocumento(cliente.getNumero())
            .nombre(cliente.getNombre())
            .direccion(cliente.getDireccion())
            .distrito(cliente.getDistrito())
            .pais(cliente.getPais())
            .email(cliente.getEmail())
            .telefono(cliente.getTelefono())
            .condicionSunat(cliente.getCondicionSunat())
            .estadoSunat(cliente.getEstadoSunat())
            .estado(cliente.isEstado())
            .build();
    }
}