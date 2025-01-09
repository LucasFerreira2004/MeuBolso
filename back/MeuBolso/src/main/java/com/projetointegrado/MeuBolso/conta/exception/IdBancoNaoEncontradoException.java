package com.projetointegrado.MeuBolso.conta.exception;

public class IdBancoNaoEncontradoException extends RuntimeException {
    public IdBancoNaoEncontradoException(String message) {
        super(message);
    }

    public IdBancoNaoEncontradoException() {
        super("banco não encontrado");
    }
}
