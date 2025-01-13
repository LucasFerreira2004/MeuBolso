package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.categoria.exception.TipoCategoriaNaoEspecificado;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

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
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        TipoTransacao tipo;
        try {
            tipo = TipoTransacao.valueOf(this.tipoTransacao);
        }catch (Exception e) {
            throw new TipoCategoriaNaoEspecificado();
        }
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
