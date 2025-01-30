package com.projetointegrado.MeuBolso.orcamento.exception;

import com.projetointegrado.MeuBolso.categoria.exception.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrcamentoExceptionHandler {

    // Trata orcamentos com categoria que não são do tipo DESPESA
    @ExceptionHandler(CategoriaOrcamentoException.class)
    public ResponseEntity<ExceptionDTO> handleCategoriaOrcamentoException(CategoriaOrcamentoException ex) {
        ExceptionDTO dto = new ExceptionDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    // Trata orcamentos com categoria repetidas par o mesmo periodo
    @ExceptionHandler(OrcamentoDuplicadoException.class)
    public ResponseEntity<ExceptionDTO> handleOrcamentoDuplicadoException(OrcamentoDuplicadoException ex) {
        ExceptionDTO dto = new ExceptionDTO(ex.getCampo(), ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
