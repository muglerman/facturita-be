package com.cna.facturita.security.service;

import com.cna.facturita.core.model.Usuario;
import com.cna.facturita.core.model.tenant.UsuarioTenant;
import com.cna.facturita.core.repository.UsuarioRepository;
import com.cna.facturita.core.repository.tenant.UsuarioRepositoryTenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.cna.facturita.multitenant.context.TenantContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepositoryTenant usuarioRepositoryTenant;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("[CustomUserDetailsService] -> [loadUserByUsername] ejecutando...");
        log.debug("[CustomUserDetailsService] -> [loadUserByUsername] Buscando usuario por correo: {}", email);
        String currentTenant = TenantContext.getCurrentTenant();
        log.debug("[CustomUserDetailsService] -> [loadUserByUsername] Tenant actual: {}", currentTenant);
        if (currentTenant == null || currentTenant.equalsIgnoreCase("default") || currentTenant.equalsIgnoreCase("cna")) {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        log.debug("[CustomUserDetailsService] -> [loadUserByUsername] ❌ Usuario no encontrado: {}", email);
                        return new UsernameNotFoundException("Usuario no encontrado: " + email);
                    });
            log.debug("[CustomUserDetailsService] -> [loadUserByUsername] ✅ Usuario encontrado usuario: {}", usuario);
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getPassword())
                    .roles("USER")
                    .build();
        } else {
            UsuarioTenant usuarioTenant = usuarioRepositoryTenant.findByEmail(email)
                    .orElseThrow(() -> {
                        log.debug("[CustomUserDetailsService] -> [loadUserByUsername] ❌ UsuarioTenant no encontrado: {}", email);
                        return new UsernameNotFoundException("UsuarioTenant no encontrado: " + email);
                    });
            log.debug("[CustomUserDetailsService] -> [loadUserByUsername] ✅ UsuarioTenant encontrado: {}", usuarioTenant);
            // Si tienes un adaptador CustomUserDetails para UsuarioTenant, úsalo aquí
            return org.springframework.security.core.userdetails.User.builder()
                    .username(usuarioTenant.getEmail())
                    .password(usuarioTenant.getPassword())
                    .roles("USER")
                    .build();
        }
    }
}