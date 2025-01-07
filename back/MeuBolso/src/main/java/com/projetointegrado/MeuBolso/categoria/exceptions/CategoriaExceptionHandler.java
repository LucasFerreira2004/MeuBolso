package com.projetointegrado.MeuBolso.categoria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class CategoriaExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NomeCadastradoException.class)
    private ResponseEntity<ExceptionDTO> nomeCadastradoHandler(NomeCadastradoException ex){
        ExceptionDTO dto = new ExceptionDTO(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NomeCadastradoException.class)
    private ResponseEntity<ExceptionDTO> nomeCadastradoException(TipoCategoriaNaoEspecificado ex){
        ExceptionDTO dto = new ExceptionDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaNaoEncontrada.class)
    private ResponseEntity<ExceptionDTO> categoriaNaoEncontradaHandler(CategoriaNaoEncontrada ex){
        ExceptionDTO dto = new ExceptionDTO(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
