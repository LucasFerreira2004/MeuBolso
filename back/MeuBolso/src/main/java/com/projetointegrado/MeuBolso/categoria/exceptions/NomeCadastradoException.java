package com.projetointegrado.MeuBolso.categoria.exceptions;

public class NomeCadastradoException extends RuntimeException{
    public NomeCadastradoException(String message) {
        super(message);
    }
    public NomeCadastradoException() {
        super("já existe uma categoria com esse nome");
    }
}