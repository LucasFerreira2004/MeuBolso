package com.projetointegrado.MeuBolso.conta.exception;

public class DescricaoJaExistenteException extends RuntimeException {
    public DescricaoJaExistenteException(String message) {
        super(message);
    }
    public DescricaoJaExistenteException() { super("descricao de conta já existente, por favor coloque um nome diferente");}
}
