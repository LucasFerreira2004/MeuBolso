package com.projetointegrado.MeuBolso.orcamento.exception;

public class CategoriaOrcamentoNaoEncontradaException extends RuntimeException {
    public CategoriaOrcamentoNaoEncontradaException(String message) {
        super(message);
    }

    public CategoriaOrcamentoNaoEncontradaException() {
        super("categoria selecionada para o orcamento é inválida");
    }
}
