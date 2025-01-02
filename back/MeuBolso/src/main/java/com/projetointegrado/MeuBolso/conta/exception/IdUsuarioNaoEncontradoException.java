package com.projetointegrado.MeuBolso.conta.exception;

public class IdUsuarioNaoEncontradoException extends RuntimeException {
    public IdUsuarioNaoEncontradoException(String message) {
        super(message);
    }
    public IdUsuarioNaoEncontradoException() {
        super("id do usuário não encontrado");
    }
}
