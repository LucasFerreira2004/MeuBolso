package com.projetointegrado.MeuBolso.globalExceptions;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
    public AcessoNegadoException() {
        super("acesso a entidade negado pois não pertence ao usuário logado");
    }
}
