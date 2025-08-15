package com.cna.facturita.api.controller;

import com.cna.facturita.api.exception.AuthenticationException;
import com.cna.facturita.api.service.AuthService;
import com.cna.facturita.dto.form.UsuarioForm;
import com.cna.facturita.dto.request.auth.LoginRequest;
import com.cna.facturita.dto.response.auth.LoginResponse;
import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://*.localhost:4200", "http://*.localhost:3000", "http://*.127.0.0.1:4200" })
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("[AuthController] -> [login] Ejecutando login...");
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("[AuthController] -> [login] Error de autenticación: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", "AUTHENTICATION_FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            log.error("[AuthController] -> [login] Error interno: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");
            errorResponse.put("error", "INTERNAL_SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Endpoint público para registrar el primer usuario del sistema.
     * Este endpoint no requiere autenticación.
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody UsuarioForm form) {
        log.info("[AuthController] -> [registro] Registrando nuevo usuario: {}", form.getEmail());

        // Verificar si el email ya existe
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(form.getEmail());
        if (usuarioExistente.isPresent()) {
            log.warn("[AuthController] -> [registro] Email ya registrado: {}", form.getEmail());
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse(false, "Ya existe un usuario con este email"));
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        form.applyTo(usuario);

        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));

        usuarioRepository.save(usuario);

        log.info("[AuthController] -> [registro] Usuario registrado exitosamente: {}", usuario.getEmail());
        return ResponseEntity.ok(new MensajeResponse(true, "Usuario registrado con éxito"));
    }

    /**
     * Endpoint para verificar si existe al menos un usuario en el sistema.
     * Útil para saber si es necesario hacer el registro inicial.
     */
    @GetMapping("/tiene-usuarios")
    public ResponseEntity<?> tieneUsuarios() {
        boolean tieneUsuarios = usuarioRepository.count() > 0;
        log.info("[AuthController] -> [tieneUsuarios] Sistema tiene usuarios: {}", tieneUsuarios);
        return ResponseEntity.ok(new TieneUsuariosResponse(tieneUsuarios));
    }

    // DTOs de respuesta
    record MensajeResponse(boolean success, String message) {
    }

    record TieneUsuariosResponse(boolean tieneUsuarios) {
    }
}