package com.projetointegrado.MeuBolso.orcamento.exception;

public class OrcamentoNaoEncontradoException extends RuntimeException {
    public OrcamentoNaoEncontradoException(String message) {
        super(message);
    }

    public OrcamentoNaoEncontradoException() {
        super("orcamento nao encontrado");
    }
}
