package com.projetointegrado.MeuBolso.globalExceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateDataException(DataIntegrityViolationException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErrorResponseDTO> handleAcessoProibidoException(AcessoNegadoException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
