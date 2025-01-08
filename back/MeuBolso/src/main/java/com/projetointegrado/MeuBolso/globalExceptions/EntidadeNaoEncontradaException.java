package com.projetointegrado.MeuBolso.globalExceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {
    private String campo;
    public EntidadeNaoEncontradaException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
