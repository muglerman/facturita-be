package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.TipoDocumentoIdentidadForm;
import com.cna.facturita.dto.validation.tenant.ConditionalTipoDocumentoIdentidadValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalTipoDocumentoIdentidadValidation} en {@link TipoDocumentoIdentidadForm}.
 */
public class ConditionalTipoDocumentoIdentidadValidationValidator implements ConstraintValidator<ConditionalTipoDocumentoIdentidadValidation, TipoDocumentoIdentidadForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalTipoDocumentoIdentidadValidationValidator.class);

    @Override
    public void initialize(ConditionalTipoDocumentoIdentidadValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalTipoDocumentoIdentidadValidationValidator");
    }

    @Override
    public boolean isValid(TipoDocumentoIdentidadForm tipoForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para TipoDocumentoIdentidadForm");
        if (tipoForm == null) {
            logger.debug("[VALIDATOR] TipoDocumentoIdentidadForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre y abreviatura no pueden ser nulos o vacíos
        if (tipoForm.nombre() == null || tipoForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        if (tipoForm.abreviatura() == null || tipoForm.abreviatura().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La abreviatura es obligatoria")
                .addPropertyNode("abreviatura")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", tipoForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
