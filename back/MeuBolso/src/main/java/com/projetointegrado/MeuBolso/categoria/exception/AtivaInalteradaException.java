package com.projetointegrado.MeuBolso.categoria.exception;

public class AtivaInalteradaException extends RuntimeException {
    private String campo;
    public AtivaInalteradaException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public AtivaInalteradaException(String campo) {
        super("impossível arquivar uma categoria já arquivada ou desarquivar uma categoria já desarquivada");
        this.campo = campo;
    }
    public String getCampo() {
        return campo;
    }
}
