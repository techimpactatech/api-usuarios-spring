package com.example.api_usuarios_spring.domain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // usa BIGSERIAL no Postgres
  private Integer id;

  @NotBlank @Size(max=255)
  private String nome;

  @NotBlank @Email @Size(max=255)
  private String email;

  @CreationTimestamp
  @Column(name = "data_criacao", updatable = false, nullable = false)
  private Instant dataCriacao;

  // getters/setters
  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }
  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public Instant getDataCriacao() { return dataCriacao; }
  public void setDataCriacao(Instant dataCriacao) { this.dataCriacao = dataCriacao; }
}
