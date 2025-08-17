package com.cna.facturita.dto.tenant;

import com.cna.facturita.core.model.tenant.UsuarioTenant;

import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record para la transferencia de datos de usuarioTenant entre capas.
 */
@Builder
public record UsuarioTenantDTO(
    @JsonProperty("id") Integer id,
    @JsonProperty("nombre") String nombre,
    @JsonProperty("email") String email,
    @JsonProperty("usuario")  String usuario,
    @JsonProperty("establecimiento_id") Integer establecimientoId,
    @JsonProperty("estado") boolean estado
) {

    /**
     * Convierte una entidad {@link Cliente} en un DTO serializable para la API.
     * @param usuarioTenant Entidad JPA de usuarioTenant
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static UsuarioTenantDTO fromEntity(UsuarioTenant usuarioTenant) {
        return UsuarioTenantDTO.builder()
            .id(usuarioTenant.getId())
            .nombre(usuarioTenant.getNombre())
            .email(usuarioTenant.getEmail())
            .usuario(usuarioTenant.getUsuario())
            .establecimientoId(usuarioTenant.getEstablecimientoId())
            .estado(usuarioTenant.isEstado())
            .build();
    }
}
