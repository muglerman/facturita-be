
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.dto.validation.tenant.ConditionalPaisValidation;
import jakarta.validation.constraints.*;

/**
 * Record para la transferencia de datos de país entre capas.
 */
@ConditionalPaisValidation
public record PaisForm(
    String id,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    Boolean estado
) {
    public void applyTo(Pais pais) {
        pais.setNombre(nombre);
        pais.setEstado(estado);
    }
}
