package com.example.api_usuarios_spring.web;

import com.example.api_usuarios_spring.domain.Usuario;
import com.example.api_usuarios_spring.service.UsuarioService;
import com.example.api_usuarios_spring.web.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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

  @Operation(summary = "Listar usuários")
  @ApiResponse(responseCode = "200", description = "Lista de usuários")
  @GetMapping
  public List<UsuarioDTO> listar(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    return service.listar(page, size).stream().map(UsuarioDTO::of).toList();
  }

  @Operation(summary = "Obter usuário por ID")
  @ApiResponse(responseCode = "200", description = "Usuário encontrado")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @GetMapping("/{id}")
  public UsuarioDTO obter(@PathVariable Long id) {
    return UsuarioDTO.of(service.buscar(id));
  }

  @Operation(summary = "Atualizar usuário por ID")
  @ApiResponse(responseCode = "200", description = "Usuário atualizado")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @ApiResponse(responseCode = "409", ref = "#/components/responses/ConflictProblem")
  @PutMapping("/{id}")
  public UsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody Usuario body) {
    return UsuarioDTO.of(service.atualizar(id, body));
  }

  @Operation(summary = "Remover usuário por ID")
  @ApiResponse(responseCode = "204", description = "Removido com sucesso")
  @ApiResponse(responseCode = "404", ref = "#/components/responses/NotFoundProblem")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long id) {
    service.remover(id);
  }
}