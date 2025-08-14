package com.cna.facturita.dto.form.auth;

import com.cna.facturita.core.model.Plan;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

/**
 * Formulario DTO para la creación y edición de planes.
 * <p>
 * Utilizado para recibir datos desde la capa de presentación y aplicar
 * validaciones antes de persistir o actualizar entidades {@link Plan}.
 * Incluye validaciones de campos y lógica condicional para reglas de negocio
 * específicas.
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>
 * PlanForm form = ...;
 * Plan plan = new Plan();
 * form.applyTo(plan);
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@com.cna.facturita.dto.auth.validation.ConditionalPlanValidation
public class PlanForm {

    /** Identificador único del plan (opcional para edición). */
    private Integer id;

    /** Nombre del plan. No puede ser nulo ni vacío. */
    @NotNull(message = "El nombre del plan es obligatorio")
    @jakarta.validation.constraints.NotBlank(message = "El nombre del plan es obligatorio")
    private String nombre;

    /** Precio del plan. Debe ser mayor o igual a cero. */
    @NotNull(message = "El nombre del plan es obligatorio")
    @jakarta.validation.constraints.DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    private double precio;

    /** Límite de usuarios permitidos. Si es 0 es ilimitado */
    @NotNull(message = "El límite de usuarios es obligatorio")
    @Min(0)
    private int limiteUsuarios;

    /** Límite de documentos permitidos. Si es 0 es ilimitado */
    @NotNull(message = "El límite de documentos es obligatorio")
    @Min(0)
    private int limiteDocumentos;

    /** Indica si se aplica el límite de documentos en notas de venta. */
    @NotNull(message = "El limite de documentos es obligatorio")
    private boolean incluirNotaVentaDocumentos;

    /** Indica si se aplica el límite de ventas en notas de venta. */
    @NotNull(message = "El indicador de limite de notas de venta del plan es obligatorio")
    private boolean incluirNotaVentaVentas;

    /** Límite de ventas permitidas. Si es 0 es ilimitado. */
    @NotNull(message = "El límite de ventas es obligatorio")
    private BigDecimal limiteVentas;

    /** Límite de establecimientos permitidos. Si es 0 es ilimitado. */
    @NotNull(message = "El limite de establecimientos es obligatorio")
    @Min(0)
    private int limiteEstablecimientos;

    /** Indica si el plan está habilitado. */
    @NotNull(message = "El estado de habilitación del plan es obligatorio")
    private boolean habilitado;

    /** Lista de tipos de documentos incluidos en el plan. */
    private List<String> documentosPlan; // JSON string

    /**
     * Aplica los datos del formulario a una entidad {@link Plan}.
     * <p>
     * Útil para mapear los datos validados del DTO a la entidad antes de persistir
     * o actualizar en la base de datos.
     *
     * @param plan Entidad de plan a modificar
     */
    public void applyTo(Plan plan) {
        plan.setNombre(nombre);
        plan.setPrecio(precio);
        plan.setLimiteUsuarios(limiteUsuarios);
        plan.setLimiteDocumentos(limiteDocumentos);
        plan.setIncluirNotaVentaDocumentos(incluirNotaVentaDocumentos);
        plan.setIncluirNotaVentaVentas(incluirNotaVentaVentas);
        plan.setLimiteVentas(limiteVentas);
        plan.setLimiteEstablecimientos(limiteEstablecimientos);
        plan.setHabilitado(habilitado);
        plan.setDocumentosPlan(documentosPlan);
    }
}