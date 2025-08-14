package com.cna.facturita.core.service;

import com.cna.facturita.core.model.Plan;
import com.cna.facturita.core.repository.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de planes de suscripción.
 */
@Service
@Transactional
public class PlanService {

    private static final Logger log = LoggerFactory.getLogger(PlanService.class);
    
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * Obtiene todos los planes activos (no bloqueados).
     */
    @Transactional(readOnly = true)
    public List<Plan> findAllActive() {
        log.debug("Obteniendo todos los planes activos");
        return planRepository.findByHabilitadoFalse();
    }

    /**
     * Obtiene todos los planes con paginación.
     */
    @Transactional(readOnly = true)
    public Page<Plan> findAll(Pageable pageable) {
        log.debug("Obteniendo planes con paginación: {}", pageable);
        return planRepository.findAll(pageable);
    }

    /**
     * Obtiene todos los planes ordenados por nombre.
     */
    @Transactional(readOnly = true)
    public List<Plan> findAllOrderByNombre() {
        log.debug("Obteniendo todos los planes ordenados por nombre");
        return planRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    /**
     * Busca un plan por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<Plan> findById(Integer id) {
        log.debug("Buscando plan con ID: {}", id);
        return planRepository.findById(id);
    }

    /**
     * Busca un plan por su nombre.
     */
    @Transactional(readOnly = true)
    public Optional<Plan> findByNombre(String nombre) {
        log.debug("Buscando plan con nombre: {}", nombre);
        return planRepository.findByNombre(nombre);
    }

    /**
     * Crea un nuevo plan.
     */
    public Plan create(Plan plan) {
        log.info("Creando nuevo plan: {}", plan.getNombre());
        
        // Verificar que no exista un plan con el mismo nombre
        if (planRepository.existsByNombre(plan.getNombre())) {
            throw new IllegalArgumentException("Ya existe un plan con el nombre: " + plan.getNombre());
        }
        
        try {
            Plan savedPlan = planRepository.save(plan);
            log.info("Plan creado exitosamente con ID: {}", savedPlan.getId());
            return savedPlan;
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad al crear plan: {}", e.getMessage());
            throw new IllegalArgumentException("Error al crear el plan: datos duplicados o inválidos");
        }
    }

    /**
     * Actualiza un plan existente.
     */
    public Plan update(Integer id, Plan planData) {
        log.info("Actualizando plan con ID: {}", id);
        
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado con ID: " + id));

        // Verificar que no exista otro plan con el mismo nombre
        Optional<Plan> planWithSameName = planRepository.findByNombre(planData.getNombre());
        if (planWithSameName.isPresent() && !planWithSameName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe otro plan con el nombre: " + planData.getNombre());
        }

        // Actualizar campos
        existingPlan.setNombre(planData.getNombre());
        existingPlan.setPrecio(planData.getPrecio());
        existingPlan.setLimiteUsuarios(planData.getLimiteUsuarios());
        existingPlan.setLimiteDocumentos(planData.getLimiteDocumentos());
        existingPlan.setIncluirNotaVentaDocumentos(planData.isIncluirNotaVentaDocumentos());
        existingPlan.setDocumentosPlan(planData.getDocumentosPlan());
        existingPlan.setHabilitado(planData.isHabilitado());
        existingPlan.setIncluirNotaVentaVentas(planData.isIncluirNotaVentaVentas());
        existingPlan.setLimiteVentas(planData.getLimiteVentas());
        existingPlan.setLimiteEstablecimientos(planData.getLimiteEstablecimientos());

        try {
            Plan updatedPlan = planRepository.save(existingPlan);
            log.info("Plan actualizado exitosamente: {}", updatedPlan.getId());
            return updatedPlan;
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad al actualizar plan: {}", e.getMessage());
            throw new IllegalArgumentException("Error al actualizar el plan: datos duplicados o inválidos");
        }
    }

    /**
     * Elimina un plan por su ID.
     */
    public void deleteById(Integer id) {
        log.info("Eliminando plan con ID: {}", id);
        
        if (!planRepository.existsById(id)) {
            throw new IllegalArgumentException("Plan no encontrado con ID: " + id);
        }

        try {
            planRepository.deleteById(id);
            log.info("Plan eliminado exitosamente: {}", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al eliminar plan (puede estar en uso): {}", e.getMessage());
            throw new IllegalArgumentException("No se puede eliminar el plan: está siendo utilizado por uno o más clientes");
        }
    }

    /**
     * Bloquea o desbloquea un plan.
     */
    public Plan toggleBloqueo(Integer id) {
        log.info("Cambiando estado de bloqueo del plan con ID: {}", id);
        
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado con ID: " + id));

        plan.setHabilitado(!plan.isHabilitado());
        Plan updatedPlan = planRepository.save(plan);

        log.info("Plan {} {}", id, updatedPlan.isHabilitado() ? "habilitado" : "deshabilitado");
        return updatedPlan;
    }

    /**
     * Busca planes por criterio de texto (nombre).
     */
    @Transactional(readOnly = true)
    public Page<Plan> search(String searchTerm, Pageable pageable) {
        log.debug("Buscando planes con término: '{}', paginación: {}", searchTerm, pageable);
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll(pageable);
        }
        
        return planRepository.findByNombreContainingIgnoreCase(searchTerm.trim(), pageable);
    }

    /**
     * Cuenta el total de planes.
     */
    @Transactional(readOnly = true)
    public long count() {
        return planRepository.count();
    }

    /**
     * Cuenta los planes activos.
     */
    @Transactional(readOnly = true)
    public long countActive() {
        return planRepository.countByHabilitadoFalse();
    }
}
