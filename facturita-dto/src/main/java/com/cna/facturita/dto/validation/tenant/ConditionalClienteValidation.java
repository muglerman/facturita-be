package com.cna.facturita.dto.validation.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cna.facturita.dto.validation.validator.tenant.ConditionalClienteValidationValidator;


/**
 * Anotación personalizada para validaciones condicionales en {@link ClienteForm}.
 */
@Documented
@Constraint(validatedBy = ConditionalClienteValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalClienteValidation {
    String message() default "Error de validación condicional en cliente";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
