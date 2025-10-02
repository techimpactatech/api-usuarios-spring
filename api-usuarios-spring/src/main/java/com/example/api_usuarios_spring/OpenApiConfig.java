package com.example.api_usuarios_spring;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI apiUsuariosOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("API de Usuários")
        .description("CRUD de usuários para comparativo de frameworks")
        .version("v1.0"));
  }
}