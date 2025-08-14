package com.cna.facturita.config.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Facturita API",
                version = "1.0",
                description = "Documentación de endpoints REST de Facturita"
        )
)
@Configuration
public class SwaggerConfig {
}