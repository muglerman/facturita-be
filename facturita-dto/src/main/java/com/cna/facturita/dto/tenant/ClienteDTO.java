
package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.Cliente;
import com.cna.facturita.core.model.tenant.Distrito;
import com.cna.facturita.core.model.tenant.Pais;
import com.cna.facturita.core.model.tenant.TipoDocumentoIdentidad;
import com.cna.facturita.dto.form.tenant.ClienteForm;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de cliente entre capas.
 */
public record ClienteDTO(
    @JsonProperty("id") Integer id,
    @JsonProperty("tipo_documento_identidad") @NotNull(message = "El tipo de documento es obligatorio") TipoDocumentoIdentidad tipoDocumentoIdentidad,
    @JsonProperty("numero_documento") @NotBlank(message = "El número de documento es obligatorio") String numeroDocumento,
    @JsonProperty("nombre") @NotBlank(message = "El nombre es obligatorio") String nombre,
    @JsonProperty("direccion") String direccion,
    @JsonProperty("distrito") Distrito distrito,
    @JsonProperty("pais") Pais pais,
    @JsonProperty("email") String email,
    @JsonProperty("telefono") String telefono,
    @JsonProperty("condicion") Boolean condicion,
    @JsonProperty("estado") Boolean estado,
    @JsonProperty("fecha_actualizacion") LocalDateTime fechaActualizacion
) {

    /**
     * Convierte una entidad {@link Cliente} en un DTO serializable para la API.
     * @param cliente Entidad JPA de cliente
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static ClienteForm fromEntity(Cliente cliente) {
        return new ClienteForm(
            cliente.getId(),
            cliente.getTipoDocumentoIdentidad(),
            cliente.getNumero(),
            cliente.getNombre(),
            cliente.getDireccion(),
            cliente.getDistrito(),
            cliente.getPais(),
            cliente.getEmail(),
            cliente.getTelefono(),
            cliente.isCondicion(),
            cliente.isEstado(),
            cliente.getFechaActualizacion()
        );
    }
}