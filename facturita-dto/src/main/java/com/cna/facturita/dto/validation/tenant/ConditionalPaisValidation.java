package com.cna.facturita.dto.validation.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cna.facturita.dto.validation.validator.tenant.ConditionalPaisValidationValidator;

/**
 * Anotación personalizada para validaciones condicionales en {@link PaisForm}.
 */
@Documented
@Constraint(validatedBy = ConditionalPaisValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalPaisValidation {
    String message() default "Error de validación condicional en país";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
