package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.PaisForm;
import com.cna.facturita.dto.validation.tenant.ConditionalPaisValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalPaisValidation} en {@link PaisForm}.
 */
public class ConditionalPaisValidationValidator implements ConstraintValidator<ConditionalPaisValidation, PaisForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalPaisValidationValidator.class);

    @Override
    public void initialize(ConditionalPaisValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalPaisValidationValidator");
    }

    @Override
    public boolean isValid(PaisForm paisForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para PaisForm");
        if (paisForm == null) {
            logger.debug("[VALIDATOR] PaisForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre no puede ser nulo o vacío
        if (paisForm.nombre() == null || paisForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", paisForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
