package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.dto.form.tenant.DistritoForm;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de distrito entre capas.
 */
public record DistritoDTO(
    @JsonProperty("id") String id,
    @JsonProperty("provincia") @NotNull(message = "La provincia es obligatoria") Provincia provincia,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("estado") Boolean estado
) {
    
    /**
     * Convierte una entidad {@link Distrito} en un DTO serializable para la API.
     * @param distrito Entidad JPA de distrito
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static DistritoForm fromEntity(Distrito distrito) {
        return new DistritoForm(
            distrito.getId(),
            distrito.getProvincia(),
            distrito.getNombre(),
            distrito.isEstado()
        );
    }
}
