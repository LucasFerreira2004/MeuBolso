package com.projetointegrado.MeuBolso.categoria.exception;

public class ExceptionDTO {
    private String campo;
    private String message;
    public ExceptionDTO(String status, String message) {
        this.campo = status;
        this.message = message;
    }

    public String getStatus() {
        return campo;
    }

    public void setStatus(String status) {
        this.campo = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
