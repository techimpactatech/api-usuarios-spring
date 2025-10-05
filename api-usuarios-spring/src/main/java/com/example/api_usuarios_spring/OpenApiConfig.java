package com.example.api_usuarios_spring;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.examples.Example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI apiUsuariosOpenAPI() {
    // ---- Schema inline (sem $ref) para problem+json ----
    var problemSchemaInline = new ObjectSchema()
        .addProperty("type",    new StringSchema().example("about:blank"))
        .addProperty("title",   new StringSchema().example("Recurso não encontrado"))
        .addProperty("status",  new IntegerSchema().format("int32").example(404))
        .addProperty("detail",  new StringSchema().example("Usuário não encontrado"))
        .addProperty("instance",new StringSchema().example("/usuarios/123"));

    // 400
    MediaType badRequestMt = new MediaType()
        .schema(problemSchemaInline)
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

    // 404
    MediaType notFoundMt = new MediaType()
        .schema(problemSchemaInline)
        .addExamples("example", new Example().value("""
          {
            "title": "Recurso não encontrado",
            "status": 404,
            "detail": "Usuário não encontrado"
          }
        """));

    // 409
    MediaType conflictMt = new MediaType()
        .schema(problemSchemaInline)
        .addExamples("example", new Example().value("""
          {
            "title": "Conflito",
            "status": 409,
            "detail": "email já cadastrado"
          }
        """));

    Components components = new Components()
        .addResponses("BadRequestProblem",
            new ApiResponse().description("Dados inválidos")
                .content(new Content().addMediaType("application/problem+json", badRequestMt)))
        .addResponses("NotFoundProblem",
            new ApiResponse().description("Recurso não encontrado")
                .content(new Content().addMediaType("application/problem+json", notFoundMt)))
        .addResponses("ConflictProblem",
            new ApiResponse().description("Conflito")
                .content(new Content().addMediaType("application/problem+json", conflictMt)));

    return new OpenAPI()
        .info(new Info()
            .title("API de Usuários")
            .description("CRUD de usuários para comparativo de frameworks")
            .version("v1.0"))
        .components(components);
  }
}