package com.example.api_usuarios_spring.service;

import com.example.api_usuarios_spring.domain.Usuario;
import com.example.api_usuarios_spring.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {
  private final UsuarioRepository repo;
  public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

  public Usuario criar(Usuario u) {
    repo.findByEmail(u.getEmail()).ifPresent(x -> {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "email já cadastrado");
    });
    return repo.save(u);
  }

  public List<Usuario> listar(int page, int size) { return repo.findAll(page, size); }

  public Usuario buscar(Long id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Usuario atualizar(Long id, Usuario dados) {
    var existente = buscar(id);
    if (!existente.getEmail().equalsIgnoreCase(dados.getEmail())) {
      repo.findByEmail(dados.getEmail()).ifPresent(x -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "email já cadastrado");
      });
    }
    existente.setNome(dados.getNome());
    existente.setEmail(dados.getEmail());
    return repo.save(existente);
  }

  public void remover(Long id) { buscar(id); repo.deleteById(id); }
}
