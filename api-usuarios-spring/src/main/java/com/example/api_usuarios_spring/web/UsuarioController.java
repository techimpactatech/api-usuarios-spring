package com.example.api_usuarios_spring.web;

import com.example.api_usuarios_spring.domain.Usuario;
import com.example.api_usuarios_spring.service.UsuarioService;
import com.example.api_usuarios_spring.web.dto.UsuarioDTO;
import com.example.api_usuarios_spring.web.dto.UsuariosPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  private final UsuarioService service;
  public UsuarioController(UsuarioService service) { this.service = service; }

  @Operation(summary = "Criar novo usuário")
  @ApiResponse(responseCode = "201", description = "Usuário criado",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)))
  @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestProblem")
  @ApiResponse(responseCode = "409", ref = "#/components/responses/ConflictProblem")
  @PostMapping
  public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody Usuario body) {
    var salvo = service.criar(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.of(salvo));
  }

  @Operation(
        summary = "Listar usuários (paginado)",
        description = "Retorna usuários com paginação.",
        parameters = {
            @Parameter(name = "page", description = "Número da página (0-based)", schema = @Schema(type="integer", defaultValue = "0")),
            @Parameter(name = "size", description = "Tamanho da página", schema = @Schema(type="integer", defaultValue = "20"))
        }
    )
    @ApiResponse(responseCode = "200", description = "Página de usuários",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UsuariosPageResponse.class)))
    @GetMapping
    public UsuariosPageResponse listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Usuario> p = service.listar(PageRequest.of(page, size));
        List<UsuarioDTO> content = p.getContent().stream().map(UsuarioDTO::of).toList();

        return new UsuariosPageResponse(
                content,
                p.getNumber(),
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages()
        );
    }

  @Operation(summary = "Obter usuário por ID")
  @ApiResponse(responseCode = "200", description = "Usuário encontrado")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @GetMapping("/{id}")
  public UsuarioDTO obter(@PathVariable Integer id) {
    return UsuarioDTO.of(service.buscar(id));
  }

  @Operation(summary = "Atualizar usuário por ID")
  @ApiResponse(responseCode = "200", description = "Usuário atualizado")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @ApiResponse(responseCode = "409", ref = "#/components/responses/ConflictProblem")
  @PutMapping("/{id}")
  public UsuarioDTO atualizar(@PathVariable Integer id, @Valid @RequestBody Usuario body) {
    return UsuarioDTO.of(service.atualizar(id, body));
  }

  @Operation(summary = "Remover usuário por ID")
  @ApiResponse(responseCode = "204", description = "Removido com sucesso")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Integer id) {
    service.remover(id);
  }
}