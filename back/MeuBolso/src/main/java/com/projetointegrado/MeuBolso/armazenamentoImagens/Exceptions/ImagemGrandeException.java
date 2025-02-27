package com.projetointegrado.MeuBolso.armazenamentoImagens.Exceptions;

public class ImagemGrandeException extends RuntimeException {
    private String campo;

    public ImagemGrandeException(String message) {
        super(message);
    }
    public ImagemGrandeException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
