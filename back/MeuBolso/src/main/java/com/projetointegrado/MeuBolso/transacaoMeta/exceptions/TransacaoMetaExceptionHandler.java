package com.projetointegrado.MeuBolso.transacaoMeta.exceptions;

import com.projetointegrado.MeuBolso.globalExceptions.ErrorResponseDTO;
import com.projetointegrado.MeuBolso.transacao.exceptions.MismatchTransacaoTypeException;
import com.projetointegrado.MeuBolso.transacao.exceptions.TipoTransacaoNaoIdentificadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransacaoMetaExceptionHandler {
    @ExceptionHandler(TipoTransacaoNaoIdentificadoException.class)
    public ResponseEntity<ErrorResponseDTO> SaldoInsuficienteException(SaldoInsuficienteException ex){
        ErrorResponseDTO dto = new ErrorResponseDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
