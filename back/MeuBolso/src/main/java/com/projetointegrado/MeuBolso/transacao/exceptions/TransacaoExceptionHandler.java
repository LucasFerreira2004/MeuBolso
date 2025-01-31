package com.projetointegrado.MeuBolso.transacao.exceptions;

import com.projetointegrado.MeuBolso.globalExceptions.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransacaoExceptionHandler {
    @ExceptionHandler(TipoTransacaoNaoIdentificadoException.class)
    public ResponseEntity<ErrorResponseDTO> tipoTransacaoNaoIdentificadoExceptionHandler(){
        ErrorResponseDTO dto = new ErrorResponseDTO("tipo transacao", "os tipos aceitos para tipo transacao devem ser RECEITA ou DESPESA");
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchTransacaoTypeException.class)
    public ResponseEntity<ErrorResponseDTO> mismatchTransacaoTypeExceptionHandler(MismatchTransacaoTypeException ex){
        ErrorResponseDTO dto = new ErrorResponseDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
