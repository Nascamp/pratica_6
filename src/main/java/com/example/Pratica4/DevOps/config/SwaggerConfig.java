package com.example.Pratica4.DevOps.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Cursos - Prática 4", version = "1.0", description = "API para gerenciamento de cursos"))
public class SwaggerConfig {
    // Com springdoc-openapi a UI estará disponível em /swagger-ui/index.html sem configuração adicional.
}
