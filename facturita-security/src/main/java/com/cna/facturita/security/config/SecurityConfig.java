package com.cna.facturita.security.config;

import com.cna.facturita.security.jwt.JwtAuthenticationFilter;
import com.cna.facturita.api.exception.CustomAccessDeniedHandler;
import com.cna.facturita.api.exception.Http401UnauthorizedEntryPoint;
import com.cna.facturita.multitenant.filter.TenantFilter;
import com.cna.facturita.security.service.CustomUserDetailsService;
import com.cna.facturita.security.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configura las reglas de seguridad para la aplicación Facturita.
 *
 * - Expone un filtro de seguridad que autentica mediante JWT (stateless).
 * - Permite acceso público a rutas como Swagger y /login.
 * - Bloquea el resto de rutas a usuarios autenticados.
 * - Registra el filtro personalizado JwtAuthenticationFilter.
 * - Configura el AuthenticationManager basado en Spring Security moderno.
 * 
 * NOTA: Usa las nuevas prácticas de Spring Security 6.1+ (sin clases
 * deprecadas).
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TenantFilter tenantFilter;
    private final Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
     private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(TenantFilter tenantFilter) {
        this.tenantFilter = tenantFilter;
    }

    /**
     * Define el filtro principal de seguridad HTTP.
     *
     * - Deshabilita CSRF (no necesario con JWT).
     * - Define rutas públicas: Swagger y /login.
     * - Todo lo demás requiere autenticación JWT.
     * - Aplica el filtro JwtAuthenticationFilter antes de
     * UsernamePasswordAuthenticationFilter.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando el filtro de seguridad HTTP...");

        return http
                // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(authAll -> authAll
                        .requestMatchers(
                                "/api/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html")
                        .permitAll()
                        // annotation in ClienteController
                        .requestMatchers("/api/tenant/cliente").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(
                    tenantFilter, null)
                .addFilterBefore(tenantFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http, String someString) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"

                        ).permitAll()
                        // .requestMatchers("/api/v1/resource").hasAnyRole("ADMIN","USER") replaced with
                        // annotation in AuthorizationController
                        .requestMatchers(HttpMethod.POST, "/api/v1/resource").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        log.info("Instanciando DaoAuthenticationProvider con BCryptPasswordEncoder...");
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * Expone el AuthenticationManager.
     *
     * Este manager será usado para autenticar credenciales con nuestro
     * CustomUserDetailsService.
     */
    @Bean
    AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        return new ProviderManager(daoAuthenticationProvider);
    }

    /**
     * Define el algoritmo de cifrado de contraseñas.
     *
     * Se utiliza BCrypt, una opción fuerte y probada contra ataques de fuerza
     * bruta.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración de CORS para permitir peticiones desde el frontend.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir orígenes específicos (puedes agregar más según necesites)
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:4200",
                "http://localhost:3000",
                "http://127.0.0.1:4200",
                "http://127.0.0.1:3000",
                "http://*.localhost:4200",
                "http://*.localhost:3000",
                "http://*.127.0.0.1:4200",
                "http://*.127.0.0.1:3000"));

        // Permitir todos los métodos HTTP
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Permitir todos los headers
        configuration.setAllowedHeaders(List.of("*"));

        // Permitir credenciales (cookies, headers de autorización)
        configuration.setAllowCredentials(true);

        // Tiempo de cache para preflight requests
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
