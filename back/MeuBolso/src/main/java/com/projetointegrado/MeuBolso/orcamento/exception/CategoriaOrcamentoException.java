package com.projetointegrado.MeuBolso.orcamento.exception;

public class CategoriaOrcamentoException extends RuntimeException {
    private final String campo;

    public CategoriaOrcamentoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public CategoriaOrcamentoException() {
        super("você deve informar uma categoria do tipo de despesas.");
        campo = "{categoria}";
    }

    public String getCampo() {
        return campo;
    }
}
