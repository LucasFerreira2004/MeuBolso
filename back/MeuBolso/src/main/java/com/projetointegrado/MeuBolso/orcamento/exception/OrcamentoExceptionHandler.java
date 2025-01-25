package com.projetointegrado.MeuBolso.orcamento.exception;

import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrcamentoExceptionHandler {

    @ExceptionHandler(OrcamentoNaoEncontradoException.class)
    public ResponseEntity<OrcamentoExceptionDTO> handleOrcamentoNaoEncontradoException(OrcamentoNaoEncontradoException ex) {
        OrcamentoExceptionDTO dto = new OrcamentoExceptionDTO("id_orcamento", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoriaRepetidaException.class)
    public ResponseEntity<OrcamentoExceptionDTO> handleCategoriaRepetidaException(CategoriaRepetidaException ex) {
        OrcamentoExceptionDTO dto = new OrcamentoExceptionDTO("categoria_repetida", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaOrcamentoNaoEncontradaException.class)
    public ResponseEntity<OrcamentoExceptionDTO> handleCategoriaOrcamentoNaoEncontradaException(CategoriaOrcamentoNaoEncontradaException ex) {
        OrcamentoExceptionDTO dto = new OrcamentoExceptionDTO("id_categoria", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<OrcamentoExceptionDTO> handleIdUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        OrcamentoExceptionDTO dto = new OrcamentoExceptionDTO("id_usuario", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
