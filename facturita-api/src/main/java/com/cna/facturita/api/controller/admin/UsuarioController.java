package com.cna.facturita.api.controller.admin;

import com.cna.facturita.dto.UsuarioDTO;
import com.cna.facturita.dto.form.auth.UsuarioForm;
import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPERADMIN')")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://127.0.0.1:4200"})
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Lista todos los usuarios registrados.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        log.info("Obteniendo lista de usuarios");
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Integer id) {
        log.info("Obteniendo usuario con ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                     .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Registra un nuevo usuario o actualiza uno existente.
     */
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioForm form) {
        log.info("Registrando usuario con email: {}", form.getEmail());
        
        // Verificar si el email ya existe (solo para nuevos usuarios)
        if (form.getId() == null) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(form.getEmail());
            if (usuarioExistente.isPresent()) {
                log.warn("Intento de registro con email ya existente: {}", form.getEmail());
                return ResponseEntity.badRequest()
                        .body(new MensajeResponse(false, "Ya existe un usuario con este email"));
            }
        }

        Usuario usuario = (form.getId() != null)
                ? usuarioRepository.findById(form.getId()).orElse(new Usuario())
                : new Usuario();

        // Aplicar datos del formulario
        form.applyTo(usuario);
        
        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));

        usuarioRepository.save(usuario);

        String mensaje = (form.getId() != null)
                ? "Usuario actualizado con éxito"
                : "Usuario registrado con éxito";

        log.info("Usuario guardado exitosamente: {}", usuario.getEmail());
        return ResponseEntity.ok(new MensajeResponse(true, mensaje));
    }

    /**
     * Elimina un usuario por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        log.info("Eliminando usuario con ID: {}", id);
        
        if (!usuarioRepository.existsById(id)) {
            log.warn("Intento de eliminar usuario inexistente con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        
        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
        return ResponseEntity.ok(new MensajeResponse(true, "Usuario eliminado con éxito"));
    }

    /**
     * Busca un usuario por email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        log.info("Buscando usuario por email: {}", email);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.map(u -> ResponseEntity.ok(UsuarioDTO.fromEntity(u)))
                     .orElse(ResponseEntity.notFound().build());
    }

    // DTO de respuesta simple
    record MensajeResponse(boolean success, String message) {}
}
