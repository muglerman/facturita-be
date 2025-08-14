package com.cna.facturita.dto.utils;

import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;


/**
 * Utilidades para validaciones condicionales en formularios y DTOs.
 * <p>
 * Proporciona métodos estáticos para aplicar reglas de negocio que dependen de la combinación de varios campos,
 * facilitando la validación avanzada en anotaciones personalizadas.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * boolean valido = ConditionalValidationUtils.validateNotBlankIfActive(form, context, "campo1", "campo2");
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
public class ConditionalValidationUtils {
    /** Logger para trazabilidad y auditoría de validaciones condicionales. */
    private static final Logger logger = LoggerFactory.getLogger(ConditionalValidationUtils.class);

    /**
     * Valida que los campos indicados no estén vacíos si el campo 'activo' es true.
     * <p>
     * Si 'activo' es true, todos los campos especificados deben tener valores no nulos y no vacíos.
     * En caso contrario, la validación pasa automáticamente.
     * <p>
     * Los errores se registran y se agregan al contexto de validación para retroalimentación en la UI.
     *
     * @param form El formulario a validar
     * @param context El contexto de validación
     * @param fields Los nombres de los campos a validar
     * @return true si todos los campos son válidos
     */
    public static boolean validateNotBlankIfActive(Object form, ConstraintValidatorContext context, String... fields) {
        try {
            Method getActivo = form.getClass().getMethod("getActivo");
            Boolean activo = (Boolean) getActivo.invoke(form);
            boolean isValid = true;
            if (Boolean.TRUE.equals(activo)) {
                for (String field : fields) {
                    Method getter = form.getClass().getMethod("get" + capitalize(field));
                    Object value = getter.invoke(form);
                    if (value == null || value.toString().isBlank()) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("El campo '" + field + "' no puede estar vacío si 'activo' es true")
                                .addPropertyNode(field)
                                .addConstraintViolation();
                        logger.warn("Validación fallida: {} vacío cuando activo es true", field);
                        isValid = false;
                    }
                }
            }
            return isValid;
        } catch (Exception e) {
            logger.error("Error en validación condicional: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Capitaliza la primera letra de una cadena.
     * <p>
     * Útil para construir nombres de métodos getter dinámicamente.
     *
     * @param str Cadena a capitalizar
     * @return Cadena con la primera letra en mayúscula
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
