
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.dto.form.tenant.ProvinciaForm;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de provincia entre capas.
 */
public record ProvinciaDTO(
    @JsonProperty("id") String id,
    @JsonProperty("departamento") @NotNull(message = "El departamento es obligatorio") Departamento departamento,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("estado") Boolean estado
) {
    
    /**
     * Convierte una entidad {@link Provincia} en un DTO serializable para la API.
     * @param provincia Entidad JPA de provincia
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static ProvinciaForm fromEntity(Provincia provincia) {
        return new ProvinciaForm(
            provincia.getId(),
            provincia.getDepartamento(),
            provincia.getNombre(),
            provincia.isEstado()
        );
    }
}
