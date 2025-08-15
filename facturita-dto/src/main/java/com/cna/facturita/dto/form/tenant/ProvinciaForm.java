
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.dto.validation.tenant.ConditionalProvinciaValidation;

import jakarta.validation.constraints.*;

/**
 * Record para la transferencia de datos de provincia entre capas.
 */
@ConditionalProvinciaValidation
public record ProvinciaForm(
    String id,
    @NotNull(message = "El departamento es obligatorio") Departamento departamento,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    Boolean estado
) {
    public void applyTo(Provincia provincia) {
        provincia.setDepartamento(departamento);
        provincia.setNombre(nombre);
        provincia.setEstado(estado);
    }
}
