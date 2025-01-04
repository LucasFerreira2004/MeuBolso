package com.projetointegrado.MeuBolso.usuario.exception;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String message) {
        super(message);
    }
    public EmailJaCadastradoException() {
        super("Email ja cadastrado");
    }

}

