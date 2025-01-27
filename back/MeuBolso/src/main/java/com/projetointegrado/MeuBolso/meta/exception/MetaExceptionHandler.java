package com.projetointegrado.MeuBolso.meta.exception;

import com.projetointegrado.MeuBolso.globalExceptions.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MetaExceptionHandler {

    @ExceptionHandler(DescricaoUnicaException.class)
    public ResponseEntity<ErrorResponseDTO> handleDescricaoUnicaException(DescricaoUnicaException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}

