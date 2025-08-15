package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.ProvinciaForm;
import com.cna.facturita.dto.validation.tenant.ConditionalProvinciaValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalProvinciaValidation} en {@link ProvinciaForm}.
 */
public class ConditionalProvinciaValidationValidator implements ConstraintValidator<ConditionalProvinciaValidation, ProvinciaForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalProvinciaValidationValidator.class);

    @Override
    public void initialize(ConditionalProvinciaValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalProvinciaValidationValidator");
    }

    @Override
    public boolean isValid(ProvinciaForm provinciaForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para ProvinciaForm");
        if (provinciaForm == null) {
            logger.debug("[VALIDATOR] ProvinciaForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre no puede ser nulo o vacío
        if (provinciaForm.nombre() == null || provinciaForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", provinciaForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
