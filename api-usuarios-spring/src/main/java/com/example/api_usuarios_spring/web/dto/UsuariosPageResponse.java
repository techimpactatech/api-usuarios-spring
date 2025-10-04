package com.example.api_usuarios_spring.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "UsuariosPage")
public record UsuariosPageResponse(
        @Schema(description = "Itens da página") List<UsuarioDTO> content,
        @Schema(example = "0") int page,
        @Schema(example = "20") int size,
        @Schema(example = "1") long totalElements,
        @Schema(example = "1") int totalPages
) {}
