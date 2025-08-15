package com.cna.facturita.dto.validation.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cna.facturita.dto.validation.validator.tenant.ConditionalProvinciaValidationValidator;

/**
 * Anotación personalizada para validaciones condicionales en {@link ProvinciaForm}.
 */
@Documented
@Constraint(validatedBy = ConditionalProvinciaValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalProvinciaValidation {
    String message() default "Error de validación condicional en provincia";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
