package com.projetointegrado.MeuBolso.conta.exception;

public class IdTipoContaNaoEncontradoException extends RuntimeException {
    public IdTipoContaNaoEncontradoException(String message) {
        super(message);
    }

    public IdTipoContaNaoEncontradoException() {
        super("tipo conta não encontrado");
    }
}
