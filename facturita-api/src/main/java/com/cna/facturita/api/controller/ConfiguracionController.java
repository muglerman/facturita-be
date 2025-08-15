package com.cna.facturita.api.controller;

import com.cna.facturita.core.model.Configuracion;
import com.cna.facturita.core.service.ConfiguracionService;
import com.cna.facturita.dto.ConfiguracionDTO;
import com.cna.facturita.dto.error.ValidationErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Controlador REST para la gestión de configuraciones del sistema.
 * <p>
 * Proporciona endpoints CRUD y logs para trazabilidad y auditoría.
 * Todas las operaciones registran eventos relevantes y errores para facilitar
 * el monitoreo y la depuración.
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>
 * curl -X GET /api/configuraciones
 * </pre>
 *
 * @author Equipo Facturita
 * @since 2025
 */
@RestController
@RequestMapping("/api/configuraciones")
public class ConfiguracionController {

    /** Logger para trazabilidad y auditoría de operaciones de configuración. */
    private static final Logger log = LoggerFactory.getLogger(ConfiguracionController.class);
    private final ConfiguracionService configuracionService;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param configuracionService Servicio de configuración
     */
    public ConfiguracionController(ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    /**
     * Obtiene la única configuración registrada en el sistema.
     * 
     * @return ConfiguracionDTO única o 404 si no existe
     */
    @GetMapping
    @Operation(summary = "Obtiene la configuración única", description = "Devuelve la única configuración registrada en el sistema.")
    public ResponseEntity<ConfiguracionDTO> getConfiguracionUnica() {
        log.info("[GET] /api/configuraciones - Consultando configuración única");
        try {
            Configuracion config = configuracionService.getConfiguracionUnica();
            if (config != null) {
                log.debug("Configuración encontrada: {}", config.getLogin());
                return ResponseEntity.ok(ConfiguracionDTO.fromEntity(config));
            } else {
                log.warn("No existe configuración registrada en el sistema");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error al consultar configuración única: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene una configuración por su ID.
     * 
     * @param id Identificador de la configuración
     * @return ConfiguracionDTO o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una configuración por ID", description = "Devuelve la configuración correspondiente al ID proporcionado.")
    public ResponseEntity<ConfiguracionDTO> getById(@PathVariable Integer id) {
        log.info("[GET] /api/configuraciones/{} - Consultando configuración por ID", id);
        try {
            return configuracionService.findById(id)
                    .map(config -> {
                        log.debug("Configuración encontrada: {}", config.getLogin());
                        return ResponseEntity.ok(ConfiguracionDTO.fromEntity(config));
                    })
                    .orElseGet(() -> {
                        log.warn("Configuración no encontrada para ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al consultar configuración por ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Crea una nueva configuración.
     * 
     * @param dto DTO de configuración
     * @return ConfiguracionDTO creada
     */
    @PostMapping
    @Operation(summary = "Crea una nueva configuración", description = "Registra una nueva configuración en el sistema.")
    public ResponseEntity<ConfiguracionDTO> create(@Valid @RequestBody ConfiguracionDTO dto) {
        log.info("[POST] /api/configuraciones - Creando configuración: {}", dto.login());
        try {
            Configuracion nueva = configuracionService.save(toEntity(dto));
            log.debug("Configuración creada con ID: {}", nueva.getId());
            return ResponseEntity.ok(ConfiguracionDTO.fromEntity(nueva));
        } catch (Exception e) {
            log.error("Error al crear configuración '{}': {}", dto.login(), e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Actualiza una configuración existente.
     * 
     * @param id  Identificador
     * @param dto DTO de configuración
     * @return ConfiguracionDTO actualizada o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una configuración", description = "Actualiza los datos de una configuración existente por su ID.")
    public ResponseEntity<ConfiguracionDTO> update(@PathVariable(value="id") Integer id, @RequestBody ConfiguracionDTO dto) {
        log.info("[PUT] /api/configuraciones/{} - Actualizando configuración", id);
        try {
            return configuracionService.findById(id)
                    .map(existing -> {
                        Configuracion updated = toEntity(dto);
                        updated.setId(id);
                        Configuracion saved = configuracionService.save(updated);
                        log.debug("Configuración actualizada: {}", saved.getLogin());
                        return ResponseEntity.ok(ConfiguracionDTO.fromEntity(saved));
                    })
                    .orElseGet(() -> {
                        log.warn("No se encontró configuración para actualizar con ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error al actualizar configuración con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    

    /**
     * Convierte un DTO en entidad {@link Configuracion}.
     * <p>
     * Útil para mapear los datos recibidos en la API a la entidad antes de
     * persistir o actualizar en la base de datos.
     *
     * @param dto DTO de configuración
     * @return Entidad Configuracion lista para persistencia
     */
    private Configuracion toEntity(ConfiguracionDTO dto) {
        Configuracion config = new Configuracion();
        config.setId(dto.id());
        config.setAdminBloqueado(dto.adminBloqueado());
        config.setCertificado(dto.certificado());
        config.setEnvioSoapId(dto.envioSoapId());
        config.setTipoSoapId(dto.tipoSoapId());
        config.setSoapUsername(dto.soapUsername());
        config.setSoapPassword(dto.soapPassword());
        config.setSoapUrl(dto.soapUrl());
        config.setTokenPublicCulqui(dto.tokenPublicCulqui());
        config.setTokenPrivateCulqui(dto.tokenPrivateCulqui());
        config.setUrlApiRuc(dto.urlApiRuc());
        config.setTokenApiRuc(dto.tokenApiRuc());
        config.setUseLoginGlobal(dto.useLoginGlobal());
        config.setLogin(dto.login());
        config.setWhatsappHabilitado(dto.whatsappHabilitado());
        config.setRegexPasswordCliente(dto.regexPasswordCliente());
        return config;
    }

    /**
     * Maneja errores de validación y devuelve una respuesta estructurada.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .toList();
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .success(false)
                .message("Datos inválidos")
                .timestamp(java.time.LocalDateTime.now())
                .errors(fieldErrors)
                .build();
        response.logError();
        return ResponseEntity.badRequest().body(response);
    }

}
