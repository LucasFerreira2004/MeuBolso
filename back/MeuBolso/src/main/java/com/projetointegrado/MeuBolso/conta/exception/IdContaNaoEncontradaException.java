package com.projetointegrado.MeuBolso.conta.exception;

public class IdContaNaoEncontradaException extends RuntimeException {
    public IdContaNaoEncontradaException(String message) {
        super(message);
    }
    public IdContaNaoEncontradaException() {
        super("conta nao encontrada");
    }

}
