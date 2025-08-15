
package com.cna.facturita.dto.form.tenant;

import com.cna.facturita.core.model.tenant.Cliente;
import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.dto.validation.tenant.ConditionalClienteValidation;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Record para la transferencia de datos de cliente entre capas.
 */
@ConditionalClienteValidation
public record ClienteForm(
    Integer id,
    @NotNull(message = "El tipo de documento es obligatorio") TipoDocumentoIdentidad tipoDocumentoIdentidad,
    @NotBlank(message = "El número de documento es obligatorio") String numeroDocumento,
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    String direccion,
    Distrito distrito,
    Pais pais,
    String email,
    String telefono,
    Boolean estado,
    LocalDateTime fechaActualizacion
) {
    public void applyTo(Cliente cliente) {
        cliente.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
        cliente.setNumero(numeroDocumento);
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setDistrito(distrito);
        cliente.setPais(pais);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setEstado(estado);
    }
}
