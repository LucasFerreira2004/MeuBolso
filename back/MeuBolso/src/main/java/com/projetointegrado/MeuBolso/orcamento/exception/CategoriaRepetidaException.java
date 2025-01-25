package com.projetointegrado.MeuBolso.orcamento.exception;

public class CategoriaRepetidaException extends RuntimeException {
    public CategoriaRepetidaException() {
        super("A categoria já está associada a um orçamento.");
    }

    public CategoriaRepetidaException(String message) {
        super(message);
    }
}
