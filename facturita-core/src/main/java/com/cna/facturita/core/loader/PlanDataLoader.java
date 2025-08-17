package com.cna.facturita.core.loader;

import com.cna.facturita.core.model.Plan;
import com.cna.facturita.core.repository.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Carga datos de demostración para planes de suscripción.
 * Se ejecuta después del DataLoader básico para asegurar que la base de datos
 * esté configurada.
 */
@Component
public class PlanDataLoader {

    private static final Logger log = LoggerFactory.getLogger(PlanDataLoader.class);

    private final PlanRepository planRepository;

    public PlanDataLoader(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public void cargaInicial() {
        log.info("=== Iniciando carga de datos demo para Planes ===");

        try {
            crearPlanesDemo();
            log.info("=== Datos demo de Planes cargados exitosamente ===");

        } catch (Exception e) {
            log.error("Error al cargar datos demo de planes: {}", e.getMessage(), e);
        }
    }

    private void crearPlanesDemo() {
        // Verificar si ya existen planes
        if (planRepository.count() > 0) {
            log.info("Ya existen planes en la base de datos. Saltando creación de datos demo.");
            return;
        }

        log.info("Creando planes de demostración...");

        // Plan Básico
        Plan planBasico = Plan.builder()
                .nombre("Básico")
                .precio(29.90)
                .limiteUsuarios(1)
                .limiteDocumentos(100)
                .habilitado(false)
                .incluirNotaVentaVentas(true)
                .incluirNotaVentaDocumentos(true)
                .documentosPlan(Arrays.asList("boleta", "nota_venta"))
                .limiteVentas(new BigDecimal("5000.00"))
                .limiteEstablecimientos(1)
                .build();

        // Plan Profesional
        Plan planProfesional = Plan.builder()
                .nombre("Profesional")
                .nombre("Básico")
                .precio(29.90)
                .limiteUsuarios(1)
                .limiteDocumentos(100)
                .habilitado(false)
                .incluirNotaVentaVentas(true)
                .incluirNotaVentaDocumentos(true)
                .documentosPlan(Arrays.asList("boleta", "nota_venta"))
                .limiteVentas(new BigDecimal("5000.00"))
                .limiteEstablecimientos(1)
                .build();

        // Plan Empresarial
        Plan planEmpresarial = Plan.builder()
                .nombre("Empresarial")
                .precio(99.90)
                .limiteUsuarios(10)
                .limiteDocumentos(2000)
                .incluirNotaVentaVentas(false)
                .documentosPlan(Arrays.asList("boleta", "factura", "nota_venta", "nota_credito", "nota_debito",
                        "guia_remision"))
                .habilitado(false)
                .incluirNotaVentaDocumentos(false)
                .limiteVentas(new BigDecimal("100000.00"))
                .limiteEstablecimientos(5)
                .build();

        // Plan Premium
        Plan planPremium = Plan.builder()
                .nombre("Premium")
                .precio(199.90)
                .limiteUsuarios(25)
                .limiteDocumentos(10000)
                .incluirNotaVentaDocumentos(false)
                .documentosPlan(Arrays.asList("boleta", "factura", "nota_venta", "nota_credito", "nota_debito",
                        "guia_remision", "retencion", "percepcion"))
                .habilitado(false)
                .incluirNotaVentaVentas(false)
                .limiteVentas(BigDecimal.ZERO)
                .limiteEstablecimientos(0)
                .build();

        // Plan Gratuito (para pruebas)
        Plan planGratuito = Plan.builder()
                .nombre("Gratuito")
                .precio(0.0)
                .limiteUsuarios(1)
                .limiteDocumentos(10)
                .incluirNotaVentaVentas(true)
                .documentosPlan(Arrays.asList("nota_venta"))
                .habilitado(false)
                .incluirNotaVentaVentas(true)
                .limiteVentas(new BigDecimal("500.00"))
                .limiteEstablecimientos(1)
                .build();

        // Plan Bloqueado (para testing)
        Plan planBloqueado = Plan.builder()
                .nombre("Plan Descontinuado")
                .precio(49.90)
                .limiteUsuarios(2)
                .limiteDocumentos(200)
                .incluirNotaVentaDocumentos(true)
                .documentosPlan(Arrays.asList("boleta", "factura"))
                .habilitado(false)
                .incluirNotaVentaVentas(true)
                .limiteVentas(new BigDecimal("10000.00"))
                .limiteEstablecimientos(5)
                .build();

        // Guardar todos los planes
        List<Plan> planes = Arrays.asList(
                planGratuito,
                planBasico,
                planProfesional,
                planEmpresarial,
                planPremium,
                planBloqueado);

        for (Plan plan : planes) {
            try {
                Plan savedPlan = planRepository.save(plan);
                log.info("✓ Plan creado: {} - ${} - {} usuarios - {} docs - {}",
                        savedPlan.getNombre(),
                        savedPlan.getPrecio(),
                        savedPlan.getLimiteUsuarios(),
                        savedPlan.getLimiteDocumentos(),
                        savedPlan.isHabilitado() ? "BLOQUEADO" : "ACTIVO");
            } catch (Exception e) {
                log.error("Error al crear plan {}: {}", plan.getNombre(), e.getMessage());
            }
        }

        log.info("Planes de demostración creados exitosamente. Total: {}", planes.size());
    }
}
