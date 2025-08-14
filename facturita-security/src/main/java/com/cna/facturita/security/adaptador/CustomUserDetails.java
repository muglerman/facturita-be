package com.cna.facturita.security.adaptador;

import com.cna.facturita.core.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Adaptador para usar la entidad Usuario con Spring Security.
 */
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
	private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Puedes agregar roles más adelante. Por ahora, vacía o con "ROLE_USER"
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword(); // Muy importante: se devuelve la contraseña encriptada
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // Puedes usar otro campo como `activo` en el futuro
    }

    public Usuario getUsuario() {
        return usuario;
    }
}

