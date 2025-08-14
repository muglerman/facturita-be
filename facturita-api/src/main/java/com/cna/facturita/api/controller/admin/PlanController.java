package com.cna.facturita.api.controller.admin;

import com.cna.facturita.core.model.Plan;
import com.cna.facturita.core.service.PlanService;
import com.cna.facturita.dto.auth.PlanDTO;
import com.cna.facturita.dto.error.ValidationErrorResponse;
import com.cna.facturita.dto.form.auth.PlanForm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de planes de suscripción.
 */
@RestController
@RequestMapping("/api/planes")
@Tag(name = "Planes", description = "Gestión de planes de suscripción")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlanController {

	private static final Logger log = LoggerFactory.getLogger(PlanController.class);

	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;

		log.debug("Plan controller");
	}

	/**
	 * Obtiene todos los planes con paginación.
	 */
	@GetMapping
	@Operation(summary = "Obtener todos los planes", description = "Obtiene una lista paginada de todos los planes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de planes obtenida exitosamente"),
			@ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<Page<PlanDTO>> getAllPlanes(
			@Parameter(description = "Número de página (comenzando desde 0)", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
			@Parameter(description = "Tamaño de página", example = "10") @RequestParam(value = "size", defaultValue = "10") int size,
			@Parameter(description = "Campo por el cual ordenar", example = "nombre") @RequestParam(value = "sortBy", defaultValue = "nombre", required = false) String sortBy,
			@Parameter(description = "Dirección del ordenamiento", example = "asc") @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		log.debug("GET /api/planes - page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);

		try {
			Sort.Direction direction = Sort.Direction.fromString(sortDir);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

			Page<Plan> planesPage = planService.findAll(pageable);
			Page<PlanDTO> planesDTOPage = planesPage.map(PlanDTO::fromEntity);

			log.debug("Planes encontrados: {} de {}", planesPage.getNumberOfElements(), planesPage.getTotalElements());
			return ResponseEntity.ok(planesDTOPage);

		} catch (IllegalArgumentException e) {
			log.error("Parámetros de paginación inválidos: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Error al obtener planes: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Obtiene todos los planes activos (sin paginación).
	 */
	@GetMapping("/activos")
	@Operation(summary = "Obtener planes activos", description = "Obtiene una lista de todos los planes no bloqueados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de planes activos obtenida exitosamente"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<List<PlanDTO>> getPlanesActivos() {
		log.debug("GET /api/planes/activos");

		try {
			List<Plan> planesActivos = planService.findAllActive();
			List<PlanDTO> planesDTOActivos = planesActivos.stream().map(PlanDTO::fromEntity)
					.collect(Collectors.toList());

			log.debug("Planes activos encontrados: {}", planesDTOActivos.size());
			return ResponseEntity.ok(planesDTOActivos);

		} catch (Exception e) {
			log.error("Error al obtener planes activos: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Obtiene un plan por su ID.
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Obtener plan por ID", description = "Obtiene un plan específico por su identificador")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Plan encontrado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Plan no encontrado"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<PlanDTO> getPlanById(
			@Parameter(description = "ID del plan", example = "1") @PathVariable(value = "id") Integer id) {

		log.debug("GET /api/planes/{}", id);

		try {
			return planService.findById(id).map(plan -> {
				PlanDTO planDTO = PlanDTO.fromEntity(plan);
				log.debug("Plan encontrado: {}", plan.getNombre());
				return ResponseEntity.ok(planDTO);
			}).orElseGet(() -> {
				log.warn("Plan no encontrado con ID: {}", id);
				return ResponseEntity.notFound().build();
			});

		} catch (Exception e) {
			log.error("Error al obtener plan por ID {}: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Crea un nuevo plan.
	 */
	@PostMapping
	@Operation(summary = "Crear nuevo plan", description = "Crea un nuevo plan de suscripción")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Plan creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
			@ApiResponse(responseCode = "409", description = "Ya existe un plan con el mismo nombre"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PlanDTO> createPlan(
			@Parameter(description = "Datos del nuevo plan") @Valid @RequestBody PlanForm planForm) {

		log.info("POST /api/planes - Creando plan: {}", planForm.getNombre());

		try {
			Plan plan = Plan.builder()
					.nombre(planForm.getNombre())
					.precio(planForm.getPrecio())
					.limiteUsuarios(planForm.getLimiteUsuarios())
					.limiteDocumentos(planForm.getLimiteDocumentos())
					.habilitado(planForm.isHabilitado())
					.limiteVentas(planForm.getLimiteVentas())
					.limiteEstablecimientos(planForm.getLimiteEstablecimientos())
					.incluirNotaVentaDocumentos(planForm.isIncluirNotaVentaDocumentos())
					.incluirNotaVentaVentas(planForm.isIncluirNotaVentaVentas())
					.documentosPlan(planForm.getDocumentosPlan())
					.build();

			Plan savedPlan = planService.create(plan);
			PlanDTO planDTO = PlanDTO.fromEntity(savedPlan);

			log.info("Plan creado exitosamente con ID: {}", savedPlan.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(planDTO);

		} catch (IllegalArgumentException e) {
			log.warn("Error de validación al crear plan: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			log.error("Error interno al crear plan: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Actualiza un plan existente.
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Actualizar plan", description = "Actualiza un plan existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Plan actualizado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
			@ApiResponse(responseCode = "404", description = "Plan no encontrado"),
			@ApiResponse(responseCode = "409", description = "Ya existe otro plan con el mismo nombre"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PlanDTO> updatePlan(
			@Parameter(description = "ID del plan a actualizar", example = "1") @PathVariable(value = "id") Integer id,
			@Parameter(description = "Nuevos datos del plan") @Valid @RequestBody PlanForm planForm) {

		log.info("PUT /api/planes/{} - Actualizando plan: {}", id, planForm.getNombre());

		try {
			Plan planData = Plan.builder()
					.nombre(planForm.getNombre())
					.precio(planForm.getPrecio())
					.limiteUsuarios(planForm.getLimiteUsuarios())
					.limiteDocumentos(planForm.getLimiteDocumentos())
					.habilitado(planForm.isHabilitado())
					.limiteVentas(planForm.getLimiteVentas())
					.limiteEstablecimientos(planForm.getLimiteEstablecimientos())
					.incluirNotaVentaDocumentos(planForm.isIncluirNotaVentaDocumentos())
					.incluirNotaVentaVentas(planForm.isIncluirNotaVentaVentas())
					.documentosPlan(planForm.getDocumentosPlan())
					.build();

			Plan updatedPlan = planService.update(id, planData);
			PlanDTO planDTO = PlanDTO.fromEntity(updatedPlan);

			log.info("Plan actualizado exitosamente: {}", id);
			return ResponseEntity.ok(planDTO);

		} catch (IllegalArgumentException e) {
			log.warn("Error de validación al actualizar plan {}: {}", id, e.getMessage());
			if (e.getMessage().contains("no encontrado")) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			log.error("Error interno al actualizar plan {}: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Elimina un plan por su ID.
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar plan", description = "Elimina un plan por su ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Plan eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Plan no encontrado"),
			@ApiResponse(responseCode = "409", description = "Plan en uso, no se puede eliminar"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deletePlan(
			@Parameter(description = "ID del plan a eliminar", example = "1") @PathVariable(value = "id") Integer id) {

		log.info("DELETE /api/planes/{}", id);

		try {
			planService.deleteById(id);
			log.info("Plan eliminado exitosamente: {}", id);
			return ResponseEntity.noContent().build();

		} catch (IllegalArgumentException e) {
			log.warn("Error al eliminar plan {}: {}", id, e.getMessage());
			if (e.getMessage().contains("no encontrado")) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			log.error("Error interno al eliminar plan {}: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Busca planes por criterio de texto.
	 */
	@GetMapping("/buscar")
	@Operation(summary = "Buscar planes", description = "Busca planes por nombre con paginación")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
			@ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<Page<PlanDTO>> searchPlanes(
			@Parameter(description = "Término de búsqueda", example = "básico") @RequestParam(required = false) String q,
			@Parameter(description = "Número de página", example = "0") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Tamaño de página", example = "10") @RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Campo por el cual ordenar", example = "nombre") @RequestParam(defaultValue = "nombre") String sortBy,
			@Parameter(description = "Dirección del ordenamiento", example = "asc") @RequestParam(defaultValue = "asc") String sortDir) {

		log.debug("GET /api/planes/buscar - q: '{}', page: {}, size: {}", q, page, size);

		try {
			Sort.Direction direction = Sort.Direction.fromString(sortDir);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

			Page<Plan> planesPage = planService.search(q, pageable);
			Page<PlanDTO> planesDTOPage = planesPage.map(PlanDTO::fromEntity);

			log.debug("Planes encontrados en búsqueda: {} de {}", planesPage.getNumberOfElements(),
					planesPage.getTotalElements());
			return ResponseEntity.ok(planesDTOPage);

		} catch (IllegalArgumentException e) {
			log.error("Parámetros de búsqueda inválidos: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Error en búsqueda de planes: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Cambia el estado de bloqueo de un plan.
	 */
	@PatchMapping("/{id}/toggle-bloqueo")
	@Operation(summary = "Cambiar estado de bloqueo", description = "Bloquea o desbloquea un plan")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Estado de bloqueo cambiado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Plan no encontrado"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PlanDTO> toggleBloqueo(
			@Parameter(description = "ID del plan", example = "1") @PathVariable(value = "id") Integer id) {

		log.info("PATCH /api/planes/{}/toggle-bloqueo", id);

		try {
			Plan updatedPlan = planService.toggleBloqueo(id);
			PlanDTO planDTO = PlanDTO.fromEntity(updatedPlan);

			log.info("Estado de bloqueo cambiado para plan {}: {}", id,
					updatedPlan.isHabilitado() ? "bloqueado" : "desbloqueado");
			return ResponseEntity.ok(planDTO);

		} catch (IllegalArgumentException e) {
			log.warn("Plan no encontrado para cambiar bloqueo: {}", id);
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			log.error("Error al cambiar estado de bloqueo del plan {}: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Obtiene estadísticas de planes.
	 */
	@GetMapping("/stats")
	@Operation(summary = "Obtener estadísticas de planes", description = "Obtiene estadísticas generales de los planes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<PlanStatsDTO> getStats() {
		log.debug("GET /api/planes/stats");

		try {
			long totalPlanes = planService.count();
			long planesActivos = planService.countActive();
			long planesBloqueados = totalPlanes - planesActivos;

			PlanStatsDTO stats = new PlanStatsDTO(totalPlanes, planesActivos, planesBloqueados);

			log.debug("Estadísticas de planes: total={}, activos={}, bloqueados={}", totalPlanes, planesActivos,
					planesBloqueados);
			return ResponseEntity.ok(stats);

		} catch (Exception e) {
			log.error("Error al obtener estadísticas de planes: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Maneja errores de validación en las peticiones.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		log.warn("Errores de validación en PlanController: {}", ex.getMessage());

		List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> ValidationErrorResponse.FieldError.builder()
						.field(error.getField())
						.rejectedValue(error.getRejectedValue())
						.message(error.getDefaultMessage())
						.build())
				.collect(Collectors.toList());

		// Agregar errores globales (por ejemplo, validaciones condicionales de clase)
		List<ValidationErrorResponse.FieldError> globalErrors = ex.getBindingResult()
				.getGlobalErrors()
				.stream()
				.map(error -> ValidationErrorResponse.FieldError.builder()
						.field(error.getObjectName())
						.rejectedValue(null)
						.message(error.getDefaultMessage())
						.build())
				.collect(Collectors.toList());

		fieldErrors.addAll(globalErrors);

		ValidationErrorResponse response = ValidationErrorResponse.builder()
				.success(false)
				.message("Errores de validación en los datos enviados")
				.timestamp(LocalDateTime.now())
				.errors(fieldErrors)
				.build();

		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * DTO para estadísticas de planes.
	 */
	public record PlanStatsDTO(long total, long activos, long bloqueados) {
	}
}
