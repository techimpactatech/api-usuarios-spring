package com.example.api_usuarios_spring.web;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ProblemDetail> handle(ResponseStatusException ex) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatusCode(),
        ex.getReason() != null ? ex.getReason() : ex.getStatusCode().toString());
    return ResponseEntity.status(ex.getStatusCode())
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(pd);
  }
}