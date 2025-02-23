package com.projetointegrado.MeuBolso.orcamento.exception;

public class OrcamentoDuplicadoException extends RuntimeException {
    private final String campo;

    public OrcamentoDuplicadoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public OrcamentoDuplicadoException() {
        super("já existe um orcamento para essa categoria nesse periodo.");
        campo = "{categoria e periodo}";
    }

    public String getCampo() {
        return campo;
    }
}
