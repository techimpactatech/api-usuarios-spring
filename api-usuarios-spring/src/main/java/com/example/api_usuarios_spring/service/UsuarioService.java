package com.example.api_usuarios_spring.service;

import com.example.api_usuarios_spring.domain.Usuario;
import com.example.api_usuarios_spring.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public Page<Usuario> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }
  public Usuario criar(Usuario u) {
    repo.findByEmail(u.getEmail()).ifPresent(x -> {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "email já cadastrado");
    });
    u.setId(null); // deixa o banco gerar
    return repo.save(u);
  }

  public List<Usuario> listarTodos() { return repo.findAll(); }

  public Usuario buscar(Integer id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
  }

  public Usuario atualizar(Integer id, Usuario dados) {
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

  public void remover(Integer id) { repo.delete(buscar(id)); }
}