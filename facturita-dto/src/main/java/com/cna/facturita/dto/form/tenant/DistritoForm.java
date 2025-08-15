
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.dto.validation.tenant.ConditionalDistritoValidation;

import jakarta.validation.constraints.*;

/**
 * Record para la transferencia de datos de distrito entre capas.
 */
@ConditionalDistritoValidation
public record DistritoForm(
    String id,
    @NotNull(message = "La provincia es obligatoria") Provincia provincia,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    Boolean estado
) {
    public void applyTo(Distrito distrito) {
        distrito.setProvincia(provincia);
        distrito.setNombre(nombre);
        distrito.setEstado(estado);
    }
}
