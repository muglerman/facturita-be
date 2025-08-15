
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.dto.validation.tenant.ConditionalTipoDocumentoIdentidadValidation;
import jakarta.validation.constraints.*;

/**
 * Record para la transferencia de datos de tipo de documento de identidad entre capas.
 */
@ConditionalTipoDocumentoIdentidadValidation
public record TipoDocumentoIdentidadForm(
    String id,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotBlank(message = "La abreviatura es obligatoria") String abreviatura,
    Boolean estado
) {
    public void applyTo(TipoDocumentoIdentidad tipo) {
        tipo.setNombre(nombre);
        tipo.setAbreviatura(abreviatura);
        tipo.setEstado(estado);
    }
}
