package com.cna.facturita.core.repository;

import com.cna.facturita.core.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

    /**
     * Busca un plan por su nombre.
     */
    Optional<Plan> findByNombre(String nombre);

    /**
     * Verifica si existe un plan con el nombre dado.
     */
    boolean existsByNombre(String nombre);

    /**
     * Obtiene todos los planes que no están bloqueados.
     */
    List<Plan> findByHabilitadoFalse();

    /**
     * Cuenta los planes que no están bloqueados.
     */
    long countByHabilitadoFalse();

    /**
     * Busca planes por nombre (ignorando mayúsculas/minúsculas) con paginación.
     */
    Page<Plan> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    /**
     * Busca planes por rango de precio.
     */
    @Query("SELECT p FROM Plan p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio")
    List<Plan> findByPrecioBetween(@Param("minPrecio") double minPrecio, @Param("maxPrecio") double maxPrecio);

    /**
     * Obtiene los planes más populares (los que tienen más clientes asignados).
     */
    @Query("SELECT p FROM Plan p LEFT JOIN Cliente c ON c.plan.id = p.id " +
            "GROUP BY p.id ORDER BY COUNT(c.id) DESC")
    List<Plan> findMostPopular();
}