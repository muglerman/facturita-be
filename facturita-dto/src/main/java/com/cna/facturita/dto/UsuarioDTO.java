package com.cna.facturita.dto;

import com.cna.facturita.core.model.Usuario;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO para la transferencia de datos de usuarios entre capas.
 * <p>
 * Representa la estructura serializada de un usuario, utilizada en la API REST y en la capa de servicios.
 * Todos los campos están documentados para facilitar el mantenimiento y la integración.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * UsuarioDTO dto = UsuarioDTO.fromEntity(usuario);
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Builder
public record UsuarioDTO(
    /** Identificador único del usuario. */
    Integer id,
    /** Nombre completo del usuario. */
    String nombre,
    /** Correo electrónico del usuario. */
    String email,
    /** Fecha en la que el correo fue verificado. */
    LocalDateTime fechaVerificacionCorreo,
    /** Fecha de creación del usuario. */
    LocalDateTime fechaDeCreacion,
    /** Fecha de última actualización del usuario. */
    LocalDateTime fechaDeActualizacion
) {
    /**
     * Convierte una entidad {@link Usuario} en un DTO serializable para la API.
     * <p>
     * Útil para exponer datos de usuarios en endpoints REST y desacoplar la capa de persistencia.
     *
     * @param usuario Entidad JPA de usuario
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static UsuarioDTO fromEntity(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .fechaVerificacionCorreo(usuario.getFechaVerificacionCorreo())
                .fechaDeCreacion(usuario.getFechaDeCreacion())
                .fechaDeActualizacion(usuario.getFechaDeActualizacion())
                .build();
    }
}
