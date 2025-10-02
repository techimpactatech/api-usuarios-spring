package com.example.api_usuarios_spring.repository.impl;

import com.example.api_usuarios_spring.domain.Usuario;
import com.example.api_usuarios_spring.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUsuarioRepository implements UsuarioRepository {
  private final Map<Long, Usuario> store = new ConcurrentHashMap<>();
  private final AtomicLong seq = new AtomicLong(0);

  @Override
  public Usuario save(Usuario u) {
    if (u.getId() == null) {
      u.setId(seq.incrementAndGet());
    }
    store.put(u.getId(), u);
    return u;
  }

  @Override
  public List<Usuario> findAll(int page, int size) {
    var all = store.values().stream()
        .sorted(Comparator.comparingLong(Usuario::getId))
        .toList();

    int from = Math.max(0, page * size);
    int to = Math.min(all.size(), from + size);
    if (from >= to) return List.of();
    return all.subList(from, to);
  }

  @Override
  public Optional<Usuario> findById(Long id) { return Optional.ofNullable(store.get(id)); }

  @Override
  public Optional<Usuario> findByEmail(String email) {
    return store.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
  }

  @Override
  public void deleteById(Long id) { store.remove(id); }

  @Override
  public long count() { return store.size(); }
}
