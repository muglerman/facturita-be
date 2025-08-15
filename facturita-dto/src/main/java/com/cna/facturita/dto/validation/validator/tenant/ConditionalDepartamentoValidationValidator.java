package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.DepartamentoForm;
import com.cna.facturita.dto.validation.tenant.ConditionalDepartamentoValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalDepartamentoValidation} en {@link DepartamentoForm}.
 */
public class ConditionalDepartamentoValidationValidator implements ConstraintValidator<ConditionalDepartamentoValidation, DepartamentoForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalDepartamentoValidationValidator.class);

    @Override
    public void initialize(ConditionalDepartamentoValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalDepartamentoValidationValidator");
    }

    @Override
    public boolean isValid(DepartamentoForm departamentoForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para DepartamentoForm");
        if (departamentoForm == null) {
            logger.debug("[VALIDATOR] DepartamentoForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre no puede ser nulo o vacío
        if (departamentoForm.nombre() == null || departamentoForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", departamentoForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
