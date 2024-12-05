package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoDTO {
    private Long id;
    private BigDecimal valor;
    private Date data_transacao;
    private TipoTransacao tipo;
    private Conta conta;
    private String comentario;
    private String descricao;

    public TransacaoDTO(Transacao transacao) {
        //isso so funcionará se os atributos forem os mesmos e estiverem na mesma ordem que a entidade
        BeanUtils.copyProperties(transacao, this);
    }

    //getters e setters
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

    public Date getData_transacao() {
        return data_transacao;
    }

    public void setData_transacao(Date data_transacao) {
        this.data_transacao = data_transacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
