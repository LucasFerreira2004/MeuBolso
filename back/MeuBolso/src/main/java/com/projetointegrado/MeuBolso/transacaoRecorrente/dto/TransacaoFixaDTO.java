package com.projetointegrado.MeuBolso.transacaoRecorrente.dto;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoFixaDTO {
    Long id;
    BigDecimal valor;
    String tipo;
    LocalDate dataCadastro;
    String descricao;
    Long contaId;

    public TransacaoFixaDTO (TransacaoRecorrente transacaoRecorrente){
        this.id = transacaoRecorrente.getId();
        this.valor = transacaoRecorrente.getValor();
        this.tipo = transacaoRecorrente.getTipo().name();
        this.dataCadastro = transacaoRecorrente.getDataCadastro();
        this.descricao = transacaoRecorrente.getDescricao();
        this.contaId = transacaoRecorrente.getConta().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }
}

