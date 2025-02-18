package com.projetointegrado.MeuBolso.meta.exception;

public class DescricaoUnicaException extends RuntimeException {
    private final String campo;

    public DescricaoUnicaException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public DescricaoUnicaException() {
        super("já existe uma meta com essa descricao.");
        campo = "{descricao}";
    }

    public String getCampo() {
        return campo;
    }
}
