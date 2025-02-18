package com.projetointegrado.MeuBolso.usuario.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
    public UsuarioNaoEncontradoException() {
        super("usuário não encontrado");
    }
}
