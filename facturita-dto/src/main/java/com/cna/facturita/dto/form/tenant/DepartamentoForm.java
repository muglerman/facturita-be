
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.dto.validation.tenant.ConditionalDepartamentoValidation;

import jakarta.validation.constraints.*;

/**
 * Record para la transferencia de datos de departamento entre capas.
 */
@ConditionalDepartamentoValidation
public record DepartamentoForm(
    String id,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    Boolean estado
) {
    public void applyTo(Departamento departamento) {
        departamento.setNombre(nombre);
        departamento.setEstado(estado);
    }
}
