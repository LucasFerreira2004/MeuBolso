package com.projetointegrado.MeuBolso.tipoConta.exception;

public class TipoContaNaoEncontradoException extends RuntimeException {
    public TipoContaNaoEncontradoException(String message) {
        super(message);
    }

    public TipoContaNaoEncontradoException() {
        super("tipo conta não encontrado");
    }
}
