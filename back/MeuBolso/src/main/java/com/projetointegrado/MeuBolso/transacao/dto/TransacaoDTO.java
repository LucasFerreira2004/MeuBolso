package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
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
    private CategoriaDTO categoriaDTO;
    private ContaDTO contaDTO;
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

    public CategoriaDTO getCategoria() {
        return categoriaDTO;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoriaDTO = categoria;
    }

    public ContaDTO getConta() {
        return contaDTO;
    }

    public void setConta(ContaDTO conta) {
        this.contaDTO = conta;
    }
}
