package com.projetointegrado.MeuBolso.categoria.exception;

public class TipoCategoriaNaoEspecificado extends RuntimeException {
    public TipoCategoriaNaoEspecificado(){
        super("tipo_categoria é um enum e deve ter o valor: 'DESPESA' ou 'RECEITA");
    }
    public TipoCategoriaNaoEspecificado(String message) {
        super(message);
    }
}
