package com.projetointegrado.MeuBolso.meta.exception;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class MetaExceptionHandler {

    @ExceptionHandler(MetaNaoEncontradaException.class)
    public ResponseEntity<?> handleMetaNaoEncontradaException(MetaNaoEncontradaException ex) {
        MetaExceptionDTO dto = new MetaExceptionDTO("id", "meta não encontrada.");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<?> handleAcessoNegadoException(AcessoNegadoException ex) {
        MetaExceptionDTO dto = new MetaExceptionDTO("id", "acesso a entidade negado pois não pertence ao usuário logado.");
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<?> handleIdUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        MetaExceptionDTO dto = new MetaExceptionDTO("token", "id usuario nao encontrado a partir do token");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    // Manipulação genérica para outros casos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error", "Ocorreu um erro interno no servidor.")
        );
    }
}

