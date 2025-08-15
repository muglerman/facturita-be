package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.DistritoForm;
import com.cna.facturita.dto.validation.tenant.ConditionalDistritoValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalDistritoValidation} en {@link DistritoForm}.
 */
public class ConditionalDistritoValidationValidator implements ConstraintValidator<ConditionalDistritoValidation, DistritoForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalDistritoValidationValidator.class);

    @Override
    public void initialize(ConditionalDistritoValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalDistritoValidationValidator");
    }

    @Override
    public boolean isValid(DistritoForm distritoForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para DistritoForm");
        if (distritoForm == null) {
            logger.debug("[VALIDATOR] DistritoForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre no puede ser nulo o vacío
        if (distritoForm.nombre() == null || distritoForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", distritoForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
