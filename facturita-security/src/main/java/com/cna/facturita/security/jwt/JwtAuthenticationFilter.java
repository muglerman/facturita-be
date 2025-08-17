package com.cna.facturita.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cna.facturita.security.service.CustomUserDetailsService;
import com.cna.facturita.security.service.JwtService;

import java.io.IOException;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // try to get JWT in cookie or in Authorization Header
        String jwt = jwtService.getJwtFromCookies(request);
        final String authHeader = request.getHeader("Authorization");

        log.info("Petición a URI: {}", request.getRequestURI());
        log.info("JWT en cookie: {}", jwt);
        log.info("Authorization header: {}", authHeader);

        if((jwt == null && (authHeader ==  null || !authHeader.startsWith("Bearer "))) || request.getRequestURI().contains("/auth")){
            log.info("No se encontró JWT o es endpoint de autenticación. Se permite la petición sin autenticación.");
            filterChain.doFilter(request, response);
            return;
        }

        // If the JWT is not in the cookies but in the "Authorization" header
        if (jwt == null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // after "Bearer "
            log.info("JWT extraído del header Authorization: {}", jwt);
        }


        final String userEmail =jwtService.extractUserName(jwt);
        log.info("Usuario extraído del JWT: {}", userEmail);
        /*
           SecurityContextHolder: is where Spring Security stores the details of who is authenticated.
           Spring Security uses that information for authorization.*/


        String path = request.getServletPath();
        if ("/auth/login".equals(path) || path.startsWith("/swagger") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        String username = null;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\": \"Su sesión ha expirado. Por favor, inicie sesión nuevamente.\", \"status\": \"401\"}");
            log.warn("JWT expirado: {}", ex.getMessage());
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Usuario autenticado: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}