package com.example.api_usuarios_spring;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI apiUsuariosOpenAPI() {
    // Reaproveita o schema nativo do Spring para ProblemDetail
    Schema<?> problemSchemaRef = new Schema<>().$ref("#/components/schemas/ProblemDetail");

    // MediaType problem+json com exemplos
    MediaType badRequestMt = new MediaType()
        .schema(problemSchemaRef)
        .addExamples("example", new Example().value("""
          {
            "title": "Requisição inválida",
            "status": 400,
            "detail": "Dados inválidos",
            "errors": {
              "email": "deve ser um e-mail válido",
              "nome": "não deve estar em branco"
            }
          }
        """));

    MediaType notFoundMt = new MediaType()
        .schema(problemSchemaRef)
        .addExamples("example", new Example().value("""
          {
            "title": "Recurso não encontrado",
            "status": 404,
            "detail": "Usuário não encontrado"
          }
        """));

    MediaType conflictMt = new MediaType()
        .schema(problemSchemaRef)
        .addExamples("example", new Example().value("""
          {
            "title": "Conflito",
            "status": 409,
            "detail": "email já cadastrado"
          }
        """));

    // Conteúdos por status
    Content badRequestContent = new Content().addMediaType("application/problem+json", badRequestMt);
    Content notFoundContent  = new Content().addMediaType("application/problem+json", notFoundMt);
    Content conflictContent  = new Content().addMediaType("application/problem+json", conflictMt);

    Components components = new Components()
        .addResponses("BadRequestProblem",
            new ApiResponse().description("Dados inválidos").content(badRequestContent))
        .addResponses("NotFoundProblem",
            new ApiResponse().description("Recurso não encontrado").content(notFoundContent))
        .addResponses("ConflictProblem",
            new ApiResponse().description("Conflito").content(conflictContent));

    return new OpenAPI()
        .info(new Info()
            .title("API de Usuários")
            .description("CRUD de usuários para comparativo de frameworks")
            .version("v1.0"))
        .components(components);
  }
}