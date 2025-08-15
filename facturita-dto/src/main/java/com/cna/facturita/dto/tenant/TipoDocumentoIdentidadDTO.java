
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.dto.form.tenant.TipoDocumentoIdentidadForm;

import jakarta.validation.constraints.*;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de tipo de documento de identidad entre capas.
 */
@Builder
public record TipoDocumentoIdentidadDTO(
    @JsonProperty("id") String id,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("abreviatura") @NotBlank(message = "La abreviatura es obligatoria") String abreviatura,
    @JsonProperty("estado") Boolean estado
) {
    

    /**
     * Convierte una entidad {@link TipoDocumentoIdentidad} en un DTO serializable para la API.
     * @param tipo Entidad JPA de tipo de documento de identidad
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static TipoDocumentoIdentidadForm fromEntity(TipoDocumentoIdentidad tipo) {
        return new TipoDocumentoIdentidadForm(
            tipo.getId(),
            tipo.getNombre(),
            tipo.getAbreviatura(),
            tipo.isEstado()
        );
    }
}
