package com.cna.facturita.dto;

import com.cna.facturita.core.model.Plan;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para la transferencia de datos de planes entre capas.
 * <p>
 * Representa la estructura serializada de un plan, utilizada en la API REST y
 * en la capa de servicios.
 * Todos los campos están documentados para facilitar el mantenimiento y la
 * integración.
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>
 * PlanDTO dto = PlanDTO.fromEntity(plan);
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@Builder
public record PlanDTO(
        /** Identificador único del plan. */
        @JsonProperty("id") Integer id,
        /** Nombre del plan. */
        @JsonProperty("nombre") String nombre,
        /** Precio del plan. */
        @JsonProperty("precio") double precio,
        /** Límite de usuarios permitidos. */
        @JsonProperty("limite_usuarios") int limiteUsuarios,
        /** Límite de documentos permitidos. */
        @JsonProperty("limite_documentos") int limiteDocumentos,
        /** Indica si el plan está habilitado. */
        @JsonProperty("habilitado") boolean habilitado,
        /** Límite de establecimientos permitidos. */
        @JsonProperty("limite_establecimientos") int limiteEstablecimientos,
        /** Límite de ventas permitido. */
        @JsonProperty("limite_ventas") BigDecimal limiteVentas,
        /** Lista de tipos de documentos incluidos en el plan. */
        @JsonProperty("documentos_plan") List<String> documentosPlan,
        /** Indica si se aplica el límite de ventas en notas de venta. */
        @JsonProperty("incluir_nota_venta_ventas") boolean incluirNotaVentaVentas,
        /** Indica si se aplica el límite de documentos en notas de venta. */
        @JsonProperty("incluir_nota_venta_documentos") boolean incluirNotaVentaDocumentos) {
    /**
     * Convierte una entidad {@link Plan} en un DTO serializable para la API.
     * <p>
     * Útil para exponer datos de planes en endpoints REST y desacoplar la capa de
     * persistencia.
     *
     * @param plan Entidad JPA de plan
     * @return DTO serializado y listo para enviar como respuesta
     */
    public static PlanDTO fromEntity(Plan plan) {
        return PlanDTO.builder()
                .id(plan.getId())
                .nombre(plan.getNombre())
                .precio(plan.getPrecio())
                .limiteUsuarios(plan.getLimiteUsuarios())
                .limiteDocumentos(plan.getLimiteDocumentos())
                .habilitado(plan.isHabilitado())
                .limiteEstablecimientos(plan.getLimiteEstablecimientos())
                .limiteVentas(plan.getLimiteVentas())
                .documentosPlan(plan.getDocumentosPlan())
                .incluirNotaVentaVentas(plan.isIncluirNotaVentaVentas())
                .incluirNotaVentaDocumentos(plan.isIncluirNotaVentaDocumentos())
                .build();
    }
}