package com.projetointegrado.MeuBolso.transacaoMeta.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    private final String campo;

    public SaldoInsuficienteException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public SaldoInsuficienteException() {
        super("já existe uma meta com essa descricao.");
        campo = "{descricao}";
    }

    public String getCampo() {
        return campo;
    }
}
