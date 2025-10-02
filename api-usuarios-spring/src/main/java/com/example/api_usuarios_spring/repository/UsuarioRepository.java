package com.example.api_usuarios_spring.repository;

import java.util.List;
import java.util.Optional;
import com.example.api_usuarios_spring.domain.Usuario;

public interface UsuarioRepository {
    Usuario save(Usuario u);
  List<Usuario> findAll(int page, int size);
  Optional<Usuario> findById(Long id);
  Optional<Usuario> findByEmail(String email);
  void deleteById(Long id);
  long count();
}
