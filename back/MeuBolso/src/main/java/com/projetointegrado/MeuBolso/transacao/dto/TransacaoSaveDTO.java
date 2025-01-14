package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.categoria.exception.TipoCategoriaNaoEspecificado;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

import com.projetointegrado.MeuBolso.transacao.exceptions.TipoTransacaoNaoIdentificadoException;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoSaveDTO {
    private BigDecimal valor;
    private Date dataTransacao;
    @NotNull(message = "O tipo de transação é obrigatório.")
    private String tipoTransacao; //está em string apenas para poder verificar o erro de TipoTransacao
    private Long categoriaId;
    private Long contaId;
    private String comentario;
    private String descricao;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataTransacao() {
        if (dataTransacao == null) {
            throw new TipoTransacaoNaoIdentificadoException(); //criar exceção depois
        }
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        TipoTransacao tipo;
        try {
            tipo = TipoTransacao.valueOf(this.tipoTransacao);
        }catch (Exception e) {
            System.out.println(e + "\n" + e.getMessage());
            throw new TipoTransacaoNaoIdentificadoException();
        }
        return tipo;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
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
}
