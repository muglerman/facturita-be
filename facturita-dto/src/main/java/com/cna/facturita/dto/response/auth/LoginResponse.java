package com.cna.facturita.dto.response.auth;

import com.cna.facturita.dto.UsuarioDTO;
import com.cna.facturita.dto.tenant.UsuarioTenantDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de autenticación en la API.
 * <p>
 * Devuelve la información del usuario autenticado, los tokens de acceso y actualización,
 * y el tiempo de expiración del token. Utilizado en endpoints de login y refresh.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * LoginResponse resp = LoginResponse.builder()
 *     .user(usuarioDTO)
 *     .token("jwt-token")
 *     .expiresIn(3600)
 *     .build();
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Schema(description = "Respuesta completa de autenticación")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /**
     * Información del usuario autenticado.
     */
    @Schema(description = "Información del usuario autenticado")
    private UsuarioDTO user;

    /**
     * Información del usuario autenticado.
     */
    @Schema(description = "Información del usuario autenticado")
    private UsuarioTenantDTO userTenant;

    /**
     * Token de acceso JWT generado tras la autenticación.
     */
    @Schema(description = "Token de acceso JWT")
    private String token;

    /**
     * Token de actualización para renovar el acceso (opcional).
     */
    @Schema(description = "Token de actualización (opcional)")
    private String refreshToken;

    /**
     * Tiempo de expiración del token en segundos.
     */
    @Schema(description = "Tiempo de expiración del token en segundos")
    private long expiresIn;

    /**
     * Log de auditoría para respuestas de login exitoso.
     * Llamar este método después de generar la respuesta.
     */
    public void logSuccess() {
       // org.slf4j.LoggerFactory.getLogger(LoginResponse.class)
         //   .info("[AUTH AUDIT] Login exitoso para usuario: {} | Expira en: {}s", user != null ? user.getEmail() : "N/A", expiresIn);
    }
}