package com.projetointegrado.MeuBolso.transacao.exceptions;

public class TipoTransacaoNaoIdentificadoException extends RuntimeException {
    public TipoTransacaoNaoIdentificadoException(String message) {
        super(message);
    }
    public TipoTransacaoNaoIdentificadoException() { super ("tipo categoria não identificado");}
}
