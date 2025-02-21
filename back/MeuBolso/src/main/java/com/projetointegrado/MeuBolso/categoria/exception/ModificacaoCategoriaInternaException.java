package com.projetointegrado.MeuBolso.categoria.exception;

public class ModificacaoCategoriaInternaException extends RuntimeException {
    public ModificacaoCategoriaInternaException(String message) {
        super(message);
    }
    public ModificacaoCategoriaInternaException() { super("acesso a categoria interna do sistema negado");}
}
