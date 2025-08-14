package com.cna.facturita.dto.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para validaciones condicionales en {@link ConfiguracionForm}.
 * <p>
 * Utiliza el validador {@link ConditionalValidationValidator} para aplicar reglas de negocio específicas de configuración.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * {@code
 * @ConditionalConfiguracionValidation
 * public class ConfiguracionForm { ... }
 * }
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Documented
@Constraint(validatedBy = ConditionalConfiguracionValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalConfiguracionValidation {
    String message() default "Error de validación condicional en configuración";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
