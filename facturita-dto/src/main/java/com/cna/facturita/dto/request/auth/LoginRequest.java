package com.cna.facturita.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para la petición de autenticación en la API.
 * <p>
 * Utilizado en el endpoint de login para recibir las credenciales del usuario.
 * Incluye anotaciones de validación y documentación OpenAPI para facilitar la integración y el testing.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * LoginRequest req = new LoginRequest();
 * req.setEmail("admin@gmail.com");
 * req.setPassword("secreto");
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Schema(description = "Petición de autenticación")
@Data
public class LoginRequest {

    /**
     * Correo electrónico del usuario que solicita autenticación.
     * No puede estar vacío.
     */
    @NotBlank
    @Schema(description = "Correo del usuario", example = "admin@gmail.com")
    private String email;

    /**
     * Contraseña del usuario.
     * No puede estar vacía.
     */
    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "********")
    private String password;

    /**
     * Log de auditoría para intentos de login.
     * Llamar este método antes de procesar la autenticación.
     */
    public void logAttempt() {
        org.slf4j.LoggerFactory.getLogger(LoginRequest.class)
            .info("[AUTH AUDIT] Intento de login para usuario: {}", email);
    }
}