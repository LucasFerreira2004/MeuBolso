package com.projetointegrado.MeuBolso.banco.dto;

import com.projetointegrado.MeuBolso.banco.Banco;
import org.springframework.beans.BeanUtils;

public class BancoDTO {
    Long id;
    String nome;
    String iconeUrl;

    public BancoDTO(Banco banco) {
        BeanUtils.copyProperties(banco, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIconeUrl() {
        return iconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }
}
