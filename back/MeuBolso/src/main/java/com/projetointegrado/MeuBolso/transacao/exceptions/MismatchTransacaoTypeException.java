package com.projetointegrado.MeuBolso.transacao.exceptions;

public class MismatchTransacaoTypeException extends RuntimeException {
    private String campo;
    public MismatchTransacaoTypeException(String message) {
      super(message);
    }
    public MismatchTransacaoTypeException(String campo, String message) {
      super(message);
      this.campo = campo;
    }

  public String getCampo() {
    return campo;
  }
}
