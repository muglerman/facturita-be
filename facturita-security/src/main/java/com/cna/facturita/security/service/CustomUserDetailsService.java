package com.cna.facturita.security.service;

import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	log.info("[CustomUserDetailsService] -> [loadUserByUsername] ejecutando...");
        log.info("[CustomUserDetailsService] -> [loadUserByUsername] Buscando usuario por correo: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                	log.error("[CustomUserDetailsService] -> [loadUserByUsername] ❌ Usuario no encontrado: {}", email);
                	return new UsernameNotFoundException("Usuario no encontrado: " + email);
                			});
        log.info("[CustomUserDetailsService] -> [loadUserByUsername] ✅ Usuario encontrado usuario: {}", usuario);
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles("USER")
                .build();
    }
}