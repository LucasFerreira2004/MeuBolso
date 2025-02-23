package com.projetointegrado.MeuBolso.conta.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(String message) {
        super(message);
    }
    public ContaNaoEncontradaException() {
        super("conta nao encontrada ");
    }

}
