package com.projetointegrado.MeuBolso.categoria.dto;

import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.categoria.exceptions.TipoCategoriaNaoEspecificado;

public class CategoriaSaveDTO {
    private String cor;
    private String nome;
    private String tipo; //tipo categoria

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        TipoCategoria tipoEnum;
        try {
            tipoEnum = TipoCategoria.valueOf(this.tipo);
        }catch (Exception e) {
            throw new TipoCategoriaNaoEspecificado();
        }
        return tipoEnum;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
