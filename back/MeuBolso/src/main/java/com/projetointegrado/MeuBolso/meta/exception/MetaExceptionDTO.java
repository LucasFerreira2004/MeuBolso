package com.projetointegrado.MeuBolso.meta.exception;

public class MetaExceptionDTO {
    private String campo;
    private String message;

    public MetaExceptionDTO(String status, String message) {
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
