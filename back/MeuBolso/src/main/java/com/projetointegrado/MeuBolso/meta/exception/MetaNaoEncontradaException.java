package com.projetointegrado.MeuBolso.meta.exception;

public class MetaNaoEncontradaException extends RuntimeException {
    public MetaNaoEncontradaException(String message) {
        super(message);
    }

    public MetaNaoEncontradaException() {
        super("meta nao encontrada");
    }
}
