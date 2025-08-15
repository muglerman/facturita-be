package com.cna.facturita.dto.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cna.facturita.dto.form.PlanForm;
import com.cna.facturita.dto.validation.ConditionalPlanValidation;

import java.math.BigDecimal;

/**
 * Validador personalizado para la anotación {@link ConditionalPlanValidation}
 * en {@link PlanForm}.
 * <p>
 * Este validador aplica reglas condicionales sobre los campos del formulario de
 * plan,
 * asegurando que ciertos campos sean obligatorios solo si el plan no es
 * ilimitado o cumple condiciones específicas.
 * <p>
 * <b>Buenas prácticas:</b>
 * <ul>
 * <li>Utiliza utilidades centralizadas para lógica de validación
 * reutilizable.</li>
 * <li>Incluye logs detallados para trazabilidad y debugging en ambientes
 * productivos.</li>
 * </ul>
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>
 * @ConditionalPlanValidation
 * public class PlanForm { ... }
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
public class ConditionalPlanValidationValidator implements ConstraintValidator<ConditionalPlanValidation, PlanForm> {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalPlanValidationValidator.class);

    @Override
    /**
     * Inicializa el validador condicional, útil para registrar el ciclo de vida del
     * validador.
     * 
     * @param constraintAnnotation Anotación de la restricción
     */
    public void initialize(ConditionalPlanValidation constraintAnnotation) {
        logger.debug(
                "[DTO] -> [Plan] -> [ConditionalPlanValidationValidator] -> [VALIDATOR INIT] Inicializando ConditionalPlanValidationValidator para PlanForm");
    }

    @Override
    /**
     * Ejecuta la validación condicional sobre el formulario de plan.
     * <p>
     * Si el formulario es nulo, la validación pasa por defecto. Si está presente,
     * se aplican las reglas
     * definidas en {@link ConditionalValidationUtils} y se registra el resultado en
     * el log.
     * <p>
     * Reglas principales:
     * <ul>
     * <li>Si ventasIlimitadas es false, limiteDeVentas debe ser mayor a 0.</li>
     * <li>El campo nombre debe estar presente si el plan está activo.</li>
     * </ul>
     *
     * @param planForm Instancia del formulario a validar
     * @param context  Contexto de validación
     * @return true si la validación es exitosa, false si falla alguna regla
     *         condicional
     */
    public boolean isValid(PlanForm planForm, ConstraintValidatorContext context) {

        if (planForm == null) {
            logger.debug("[VALIDATOR] PlanForm es null, validación exitosa por defecto");
            return true;
        }

        logger.debug(
                "[DTO] -> [Plan] -> [ConditionalPlanValidationValidator] -> [IsValid] Iniciando validación condicional para PlanForm");

        boolean isValid,
                nombreValido = true,
                precioValido = true,
                limiteUsuariosValido = true,
                limiteDocumentosValido = true,
                isIncluirNotaVentaDocumentosValido = true,
                isIncluirNotaVentaVentasValido = true,
                habilitado = true,
                limiteVentasValido = true,
                limiteEstablecimientosValido = true;

        // Validar que el nombre esté presente y no sea vacío
        if (planForm.getNombre() == null || planForm.getNombre().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El nombre del plan es obligatorio")
                    .addPropertyNode("nombre")
                    .addConstraintViolation();
            logger.warn("[VALIDATOR-KO] Validación fallida: nombre es null o vacío");
            nombreValido = false;
        } else {
            logger.debug("[VALIDATOR-OK] Nombre de plan válido: {}", planForm.getNombre());
        }

        // Validar que precio sea un número entero mayor o igual a 0
        if (planForm.getPrecio() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El precio debe ser un número mayor o igual a 0")
                    .addPropertyNode("precio")
                    .addConstraintViolation();
            precioValido = false;
            logger.warn("[VALIDATOR-KO] Validación fallida: precio es menor a 0");
        } else {
            logger.debug("[VALIDATOR-OK] Precio de plan válido: {}", planForm.getPrecio());
        }

        // Validar que limiteUsuarios sea un número entero mayor o igual a 0
        if (planForm.getLimiteUsuarios() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El límite de usuarios debe ser un número entero mayor o igual a 0")
                    .addPropertyNode("limiteUsuarios")
                    .addConstraintViolation();
            limiteUsuariosValido = false;
            logger.warn("[VALIDATOR-KO] Validación fallida: límite de usuarios es menor a 0");
        } else {
            logger.debug("[VALIDATOR-OK] Límite de usuarios válido: {}", planForm.getLimiteUsuarios());
        }

        // Validar que limiteDocumentos sea un número entero mayor o igual a 0
        if (planForm.getLimiteDocumentos() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El límite de documentos debe ser un número entero mayor o igual a 0")
                    .addPropertyNode("limiteDocumentos")
                    .addConstraintViolation();
            limiteDocumentosValido = false;
            logger.warn("[VALIDATOR-KO] Validación fallida: límite de documentos es menor a 0");
        } else if (planForm.getLimiteDocumentos() > 0) {
            if (planForm.isIncluirNotaVentaDocumentos() || !planForm.isIncluirNotaVentaDocumentos()) {
                logger.debug("[VALIDATOR-OK] Incluir nota de venta para documentos válido: {}",
                        planForm.isIncluirNotaVentaDocumentos());
            } else {
                // Validar que se no incluya nota de venta para documentos ilimitados
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "[VALIDATOR-KO] Incluir nota de venta para documentos no es válido")
                        .addPropertyNode("incluirNotaVentaDocumentos")
                        .addConstraintViolation();
                isIncluirNotaVentaDocumentosValido = false;
                logger.warn("[VALIDATOR-KO] Validación fallida: incluir notas de ventas para documentos ilimitados");
            }
        } else {
            logger.debug("[VALIDATOR-OK] Límite de documentos válido: {}", planForm.getLimiteDocumentos());
        }

        // Validar que habilitado sea verdadero o falso
        if (planForm.isHabilitado()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El estado de habilitación debe ser verdadero o falso")
                    .addPropertyNode("habilitado")
                    .addConstraintViolation();
            habilitado = false;
            logger.warn("[VALIDATOR-KO] Validación fallida: habilitado es menor a 0");
        } else {
            logger.debug("[VALIDATOR-OK] Estado de habilitación válido: {}", planForm.isHabilitado());
        }

        // Validar que limiteVentas sea mayor a 0
        if (planForm.getLimiteVentas().compareTo(BigDecimal.ZERO) == 0) {
            logger.debug("[VALIDATOR] Ventas ilimitadas habilitadas");

            // Validar que se no incluya nota de venta para ventas ilimitadas
            if (planForm.isIncluirNotaVentaVentas()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "No se debe marcar incluir nota de venta para ventas ilimitadas")
                        .addPropertyNode("incluirNotaVentaVentas")
                        .addConstraintViolation();
                logger.warn("[VALIDATOR-KO] Validación fallida: incluir notas de ventas para ventas ilimitadas");
                isIncluirNotaVentaVentasValido = false;
            } else {
                logger.debug("[VALIDATOR-OK] Incluir nota de venta para ventas válido: {}",
                        planForm.isIncluirNotaVentaVentas());
            }
        } else if (planForm.getLimiteVentas().compareTo(BigDecimal.ZERO) > 0) {
            logger.debug("[VALIDATOR-OK] Ventas limitadas habilitadas");

            // Validar que si se incluya nota de venta para ventas limitadas
            if (planForm.isIncluirNotaVentaVentas() || !planForm.isIncluirNotaVentaVentas()) {
                logger.debug("[VALIDATOR-OK] Incluir nota de venta para ventas válido: {}",
                        planForm.isIncluirNotaVentaVentas());
            } else {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Incluir nota de venta para ventas no es válido")
                        .addPropertyNode("incluirNotaVentaVentas")
                        .addConstraintViolation();
                logger.warn("[VALIDATOR-KO] Validación fallida: incluir notas de ventas para ventas no es válido");
                isIncluirNotaVentaVentasValido = false;
            }
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El límite de ventas debe ser mayor a 0")
                    .addPropertyNode("limiteVentas")
                    .addConstraintViolation();
            logger.warn("[VALIDATOR-KO] Validación fallida: el límite de ventas debe ser mayor a 0");
            limiteVentasValido = false;
            logger.debug("[VALIDATOR-OK] Límite de documentos válido: {}", planForm.getLimiteVentas());
        }

        // Validar que limiteEstablecimientos sea un número entero mayor o igual a 0
        if (planForm.getLimiteEstablecimientos() < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "El límite de establecimientos debe ser un número entero mayor o igual a 0")
                    .addPropertyNode("limiteEstablecimientos")
                    .addConstraintViolation();
            limiteEstablecimientosValido = false;
            logger.warn("[VALIDATOR-KO] Validación fallida: límite de establecimientos es menor a 0");
        } else {
            logger.debug("[VALIDATOR-OK] Límite de establecimientos válido: {}", planForm.getLimiteEstablecimientos());
        }

        isValid = nombreValido && precioValido && limiteUsuariosValido && limiteDocumentosValido
                && limiteEstablecimientosValido && isIncluirNotaVentaVentasValido && isIncluirNotaVentaDocumentosValido
                && habilitado && limiteVentasValido;

        logger.info("[VALIDATOR] Resultado de validación condicional para id={}: {}", planForm.getId(),
                isValid ? "OK" : "ERROR");

        return isValid;
    }
}
