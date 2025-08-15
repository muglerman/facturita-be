package com.cna.facturita.dto.validation.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cna.facturita.dto.validation.validator.tenant.ConditionalDistritoValidationValidator;

/**
 * Anotación personalizada para validaciones condicionales en {@link DistritoForm}.
 */
@Documented
@Constraint(validatedBy = ConditionalDistritoValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalDistritoValidation {
    String message() default "Error de validación condicional en distrito";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
