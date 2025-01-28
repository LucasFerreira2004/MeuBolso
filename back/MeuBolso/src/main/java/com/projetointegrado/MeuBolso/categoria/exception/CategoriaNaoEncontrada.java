package com.projetointegrado.MeuBolso.categoria.exception;

import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;

public class CategoriaNaoEncontrada extends EntidadeNaoEncontradaException {
    public CategoriaNaoEncontrada(String campo, String mensagem) {
        super(campo, mensagem);
    }

}
