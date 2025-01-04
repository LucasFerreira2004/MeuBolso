package com.projetointegrado.MeuBolso.conta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContaExceptionHandler {
    @ExceptionHandler(IdBancoNaoEncontradoException.class)
    public ResponseEntity<?> handleIdBancoNaoEncontradoException(IdBancoNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("id_banco", "id banco nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdTipoContaNaoEncontradoException.class)
    public ResponseEntity<?> handleIdTipoContaNaoEncontradoException(IdTipoContaNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("id_tipo_conta", "id tipo conta nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdUsuarioNaoEncontradoException.class)
    public ResponseEntity<?> handleIdUsuarioNaoEncontradoException(IdUsuarioNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("token", "id usuario nao encontrado a partir do token");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdContaNaoEncontradaException.class)
    public ResponseEntity<?> handleIdContaNaoEncontradaException(IdContaNaoEncontradaException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("/id", "id conta nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AcessoContaNegadoException.class)
    public ResponseEntity<?> handleAcessoContaNegadoException(AcessoContaNegadoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("/id", ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }
}
