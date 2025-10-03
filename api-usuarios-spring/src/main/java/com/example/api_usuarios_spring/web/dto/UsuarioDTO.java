package com.example.api_usuarios_spring.web.dto;

import com.example.api_usuarios_spring.domain.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(name = "Usuario")
public record UsuarioDTO(
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1") Integer id,
  @Schema(example = "Ana Silva") String nome,
  @Schema(example = "ana@exemplo.com") String email,
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "2025-10-02T23:59:59Z") Instant dataCriacao
) {
  public static UsuarioDTO of(Usuario u) {
    return new UsuarioDTO(u.getId(), u.getNome(), u.getEmail(), u.getDataCriacao());
  }
}
