
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.dto.form.tenant.PaisForm;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de país entre capas.
 */
public record PaisDTO(
    @JsonProperty("id") String id,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("estado") Boolean estado
) {
    
    /**
     * Convierte una entidad {@link Pais} en un DTO serializable para la API.
     * @param pais Entidad JPA de país
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static PaisForm fromEntity(Pais pais) {
        return new PaisForm(
            pais.getId(),
            pais.getNombre(),
            pais.isEstado()
        );
    }
}
