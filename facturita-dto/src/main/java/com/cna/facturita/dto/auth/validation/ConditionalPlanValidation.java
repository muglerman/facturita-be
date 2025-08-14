package com.cna.facturita.dto.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para validaciones condicionales en {@link PlanForm}.
 * <p>
 * Utiliza el validador {@link ConditionalValidationValidator} para aplicar reglas de negocio específicas del plan.
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * {@code
 * @ConditionalPlanValidation
 * public class PlanForm { ... }
 * }
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Documented
@Constraint(validatedBy = ConditionalPlanValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalPlanValidation {
    String message() default "Error de validación condicional en plan";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
