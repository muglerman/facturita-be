package com.cna.facturita.dto.validation.validator.tenant;

import com.cna.facturita.dto.form.tenant.ClienteForm;
import com.cna.facturita.dto.validation.tenant.ConditionalClienteValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalClienteValidation} en {@link ClienteForm}.
 */
public class ConditionalClienteValidationValidator implements ConstraintValidator<ConditionalClienteValidation, ClienteForm> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalClienteValidationValidator.class);

    @Override
    public void initialize(ConditionalClienteValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalClienteValidationValidator");
    }

    @Override
    public boolean isValid(ClienteForm clienteForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para ClienteForm");
        if (clienteForm == null) {
            logger.debug("[VALIDATOR] ClienteForm es null, validación exitosa por defecto");
            return true;
        }
        boolean isValid = true;
        // Ejemplo: nombre y numeroDocumento no pueden ser nulos o vacíos
        if (clienteForm.nombre() == null || clienteForm.nombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre es obligatorio")
                .addPropertyNode("nombre")
                .addConstraintViolation();
            isValid = false;
        }
        if (clienteForm.numeroDocumento() == null || clienteForm.numeroDocumento().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El número de documento es obligatorio")
                .addPropertyNode("numeroDocumento")
                .addConstraintViolation();
            isValid = false;
        }
        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", clienteForm.id(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
