package com.projetointegrado.MeuBolso.categoria.dto;

import com.projetointegrado.MeuBolso.categoria.TipoCategoria;

public class CategoriaPostDTO {
    private String cor;
    private String nome;
    private TipoCategoria tipo;

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNomme(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }
}
