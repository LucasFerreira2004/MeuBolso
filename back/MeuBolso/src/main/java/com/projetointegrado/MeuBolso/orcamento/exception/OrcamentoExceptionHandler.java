package com.projetointegrado.MeuBolso.orcamento.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class OrcamentoExceptionHandler {

    // Trata orcamentos com categoria repetidas par o mesmo periodo
//    @ExceptionHandler(UsuarioNaoEncontradoException.class)
//    public ResponseEntity<ExceptionDTO> handleRuntimeException(RuntimeException ex) {
//        ExceptionDTO dto = new ExceptionDTO(ex.getMessage(), ex.getMessage());
//        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
//    }
}
