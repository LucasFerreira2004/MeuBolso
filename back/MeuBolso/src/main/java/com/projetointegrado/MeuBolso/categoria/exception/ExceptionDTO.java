package com.projetointegrado.MeuBolso.categoria.exception;

public class ExceptionDTO {
    private String campo;
    private String message;
    public ExceptionDTO(String campo, String message) {
        this.campo = campo;
        this.message = message;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String status) {
        this.campo = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
