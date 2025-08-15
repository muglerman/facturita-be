package com.cna.facturita.dto.validation.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.cna.facturita.dto.validation.validator.tenant.ConditionalDepartamentoValidationValidator;

/**
 * Anotación personalizada para validaciones condicionales en {@link DepartamentoForm}.
 */
@Documented
@Constraint(validatedBy = ConditionalDepartamentoValidationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalDepartamentoValidation {
    String message() default "Error de validación condicional en departamento";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
