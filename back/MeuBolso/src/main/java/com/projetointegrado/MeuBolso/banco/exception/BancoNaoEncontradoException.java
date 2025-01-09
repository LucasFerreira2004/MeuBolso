package com.projetointegrado.MeuBolso.banco.exception;

public class BancoNaoEncontradoException extends RuntimeException {
    public BancoNaoEncontradoException(String message) {
        super(message);
    }

    public BancoNaoEncontradoException() {
        super("banco não encontrado");
    }
}
