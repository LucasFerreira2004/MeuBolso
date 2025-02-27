package com.projetointegrado.MeuBolso.armazenamentoImagens.Exceptions;

import com.projetointegrado.MeuBolso.autorizacao.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArmazenamentoImagensExceptionHandler {
    @ExceptionHandler(ImagemGrandeException.class)
    public ResponseEntity<ErrorResponseDTO> imagemGrandeExceptionHandler(ImagemGrandeException e){
        ErrorResponseDTO dto = new ErrorResponseDTO(e.getCampo(), e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
