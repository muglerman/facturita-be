package com.cna.facturita.api.service;

import com.cna.facturita.api.exception.AuthenticationException;
import com.cna.facturita.dto.UsuarioDTO;
import com.cna.facturita.dto.request.auth.LoginRequest;
import com.cna.facturita.dto.response.auth.LoginResponse;
import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;
import com.cna.facturita.security.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public LoginResponse login(LoginRequest request) throws AuthenticationException {
    	logger.info("[AuthService]  -> [LoginResponse]: Ejecutando authenticate()...");
    	 
    	 try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        logger.info("[AuthService] -> [LoginResponse]: Asignado a userDetails");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Obtener el usuario de la base de datos
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

        // Generar token
        String token = jwtService.generateToken(userDetails.getUsername());

        // Construir respuesta completa
        return LoginResponse.builder()
                .user(UsuarioDTO.fromEntity(usuario))
                .token(token)
                .refreshToken(null) // Por ahora no implementamos refresh token
                .expiresIn(jwtService.getExpirationInSeconds())
                .build();

    	 } catch (BadCredentialsException e) {
    		 logger.error("[AuthService] -> Credenciales inválidas para el usuario: {}", request.getEmail());
    	     throw new AuthenticationException("Credenciales inválidas");
    	 } catch (Exception e) {
    		 logger.error("[AuthService] -> Error al autenticar: {}", e.getMessage());
    	     throw new AuthenticationException("Error en la autenticación: " + e.getMessage());
    	 }
    }
}