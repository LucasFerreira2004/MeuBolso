package com.projetointegrado.MeuBolso.conta.exception;

public class AcessoContaNegadoException extends RuntimeException {
    public AcessoContaNegadoException(String message) {
        super(message);
    }
    public AcessoContaNegadoException() {
        super("acesso a conta negado");
    }

}
