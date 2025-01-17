package com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.TransacaoFixa;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoFixaDTO {
    Long id;
    BigDecimal valor;
    String tipo;
    Date dataCadastro;
    String descricao;
    Long contaId;

    public TransacaoFixaDTO (TransacaoFixa transacaoFixa){
        this.id = transacaoFixa.getId();
        this.valor = transacaoFixa.getValor();
        this.tipo = transacaoFixa.getTipo().name();
        this.dataCadastro = transacaoFixa.getDataCadastro();
        this.descricao = transacaoFixa.getDescricao();
        this.contaId = transacaoFixa.getConta().getId();
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
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

