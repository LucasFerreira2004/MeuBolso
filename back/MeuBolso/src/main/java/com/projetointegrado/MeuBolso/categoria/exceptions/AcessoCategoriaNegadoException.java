package com.projetointegrado.MeuBolso.categoria.exceptions;

public class AcessoCategoriaNegadoException extends RuntimeException {
    public AcessoCategoriaNegadoException(String message) {
        super(message);
    }
    public AcessoCategoriaNegadoException() {
        super("acesso a cetegoria negado");
    }

}
