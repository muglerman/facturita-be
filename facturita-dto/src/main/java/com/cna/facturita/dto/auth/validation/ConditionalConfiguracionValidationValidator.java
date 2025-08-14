package com.cna.facturita.dto.auth.validation;

import com.cna.facturita.dto.form.auth.ConfiguracionForm;
import com.cna.facturita.dto.utils.ConditionalValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador personalizado para la anotación {@link ConditionalConfiguracionValidation} en {@link ConfiguracionForm}.
 * <p>
 * Este validador permite aplicar reglas condicionales sobre los campos del formulario de configuración,
 * asegurando que ciertos campos sean obligatorios solo si la configuración está activa o cumple condiciones específicas.
 * <p>
 * <b>Buenas prácticas:</b>
 * <ul>
 *   <li>Utiliza utilidades centralizadas para lógica de validación reutilizable.</li>
 *   <li>Incluye logs detallados para trazabilidad y debugging en ambientes productivos.</li>
 * </ul>
 * <p>
 * <b>Ejemplo de uso:</b>
 * <pre>
 * @ConditionalConfiguracionValidation
 * public class ConfiguracionForm { ... }
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
public class ConditionalConfiguracionValidationValidator implements ConstraintValidator<ConditionalConfiguracionValidation, ConfiguracionForm> {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalConfiguracionValidationValidator.class);

    @Override
    public void initialize(ConditionalConfiguracionValidation constraintAnnotation) {
        logger.debug("[VALIDATOR INIT] Inicializando ConditionalConfiguracionValidationValidator");
    }

    @Override
    public boolean isValid(ConfiguracionForm configForm, ConstraintValidatorContext context) {
        logger.debug("[VALIDATOR] Iniciando validación condicional para ConfiguracionForm");

        if (configForm == null) {
            logger.debug("[VALIDATOR] ConfiguracionForm es null, validación exitosa por defecto");
            return true;
        }

        boolean isValid = ConditionalValidationUtils.validateNotBlankIfActive(configForm, context, "nombre", "valor");

        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", configForm.getId(), isValid ? "OK" : "ERROR");
        return isValid;
    }
}
