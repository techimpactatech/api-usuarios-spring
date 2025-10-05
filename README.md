🟡 api-usuarios-spring — Spring Boot (Java)
📌 Descrição

CRUD de Usuários em Spring Boot, com paginação ?page&size, tratamento de erros no padrão Problem Detail e Swagger UI.

✅ Pré-requisitos

Java 17+

Maven 3.9+

Docker (para subir o Postgres)

Postgres local (ou o container abaixo)

🐘 Banco de dados (Docker)

Se ainda não tiver o Postgres rodando:

docker run --name tcc-postgres ^
-e POSTGRES_DB=tcc_framework ^
-e POSTGRES_USER=tcc_user ^
-e POSTGRES_PASSWORD=tcc_pass ^
-p 5433:5432 ^
-v tcc_data:/var/lib/postgresql/data ^
-d postgres:15

⚙️ Configuração

src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5433/tcc_framework
spring.datasource.username=tcc_user
spring.datasource.password=tcc_pass

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Swagger (springdoc)
springdoc.swagger-ui.path=/swagger-ui.html

▶️ Rodando a aplicação
mvn clean install
mvn spring-boot:run

🔗 Endpoints

Swagger: http://localhost:8080/swagger-ui.html

Listar paginado: GET /usuarios?page=0&size=20

Demais: POST /usuarios, GET /usuarios/{id}, PUT /usuarios/{id}, DELETE /usuarios/{id}

📦 Estrutura (resumo)
domain/ Usuario.java
repository/ UsuarioRepository.java
service/ UsuarioService.java
web/ UsuarioController.java, ApiExceptionHandler.java
web/dto/ UsuarioDTO.java, UsuariosPageResponse.java
OpenApiConfig.java
