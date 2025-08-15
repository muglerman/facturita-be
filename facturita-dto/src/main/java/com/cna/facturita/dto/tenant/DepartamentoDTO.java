
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.dto.form.tenant.DepartamentoForm;

import jakarta.validation.constraints.*;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de departamento entre capas.
 */
@Builder
public record DepartamentoDTO(
    @JsonProperty("id") String id,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("estado") Boolean estado
) {
    

    /**
     * Convierte una entidad {@link Departamento} en un DTO serializable para la API.
     * @param departamento Entidad JPA de departamento
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static DepartamentoForm fromEntity(Departamento departamento) {
        return new DepartamentoForm(
            departamento.getId(),
            departamento.getNombre(),
            departamento.isEstado()
        );
    }
}
