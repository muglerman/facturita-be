package com.cna.facturita.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para la respuesta de errores de validación en la API.
 * <p>
 * Estructura estándar para devolver información detallada sobre los errores de validación
 * ocurridos en los endpoints, incluyendo el timestamp, mensaje general y lista de errores por campo.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * ValidationErrorResponse response = ValidationErrorResponse.builder()
 *     .success(false)
 *     .message("Datos inválidos")
 *     .timestamp(LocalDateTime.now())
 *     .errors(List.of(new FieldError("email", "", "El email es obligatorio")))
 *     .build();
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    /** Indica si la operación fue exitosa (siempre false en errores de validación). */
    private boolean success;

    /** Mensaje general del error de validación. */
    private String message;

    /** Timestamp en que ocurrió el error. */
    private LocalDateTime timestamp;

    /** Lista de errores específicos por campo. */
    private List<FieldError> errors;

    /**
     * Log de auditoría para respuestas de error de validación.
     * Llamar este método al construir la respuesta.
     */
    public void logError() {
        org.slf4j.LoggerFactory.getLogger(ValidationErrorResponse.class)
            .warn("[VALIDATION ERROR] {} | Campos: {}", message, errors != null ? errors.size() : 0);
    }

    /**
     * Estructura para detallar el error de cada campo inválido.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        /** Nombre del campo con error. */
        private String field;
        /** Valor rechazado por la validación. */
        private Object rejectedValue;
        /** Mensaje descriptivo del error. */
        private String message;
    }
}
