package com.projetointegrado.MeuBolso.globalExceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateDataException(DataIntegrityViolationException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErrorResponseDTO> handleAcessoProibidoException(AcessoNegadoException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("/{id}", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //exceção lançada quando o @Valid falha
    public ResponseEntity<List<ErrorResponseDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorResponseDTO> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorResponseDTO(fieldName, errorMessage));
                    System.out.println(new ErrorResponseDTO(fieldName, errorMessage));
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
