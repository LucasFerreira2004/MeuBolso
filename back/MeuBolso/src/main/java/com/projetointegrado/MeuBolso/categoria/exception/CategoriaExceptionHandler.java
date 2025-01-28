package com.projetointegrado.MeuBolso.categoria.exception;

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
        ExceptionDTO dto = new ExceptionDTO("nome", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TipoCategoriaNaoEspecificado.class)
    private ResponseEntity<ExceptionDTO> tipoCategoriaNaoEspecificadoHandler(TipoCategoriaNaoEspecificado ex){
        ExceptionDTO dto = new ExceptionDTO("tipo categoria", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaNaoEncontrada.class)
    private ResponseEntity<ExceptionDTO> categoriaNaoEncontradaHandler(CategoriaNaoEncontrada ex){
        ExceptionDTO dto = new ExceptionDTO("/{id}", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModificacaoCategoriaInternaException.class)
    private ResponseEntity<ExceptionDTO> modificandoCategoriaInternaHandler(ModificacaoCategoriaInternaException ex){
        ExceptionDTO dto = new ExceptionDTO("/{id}", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

}
