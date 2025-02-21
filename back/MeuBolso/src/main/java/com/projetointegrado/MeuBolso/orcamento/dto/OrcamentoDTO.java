package com.projetointegrado.MeuBolso.orcamento.dto;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.orcamento.Orcamento;
import com.projetointegrado.MeuBolso.orcamento.notifications.NotificacaoOrcamento;
import com.projetointegrado.MeuBolso.orcamento.notifications.NotificacaoOrcamentoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDTO {
    private Long id;
    private String descricao;
    private BigDecimal valorEstimado;
    private BigDecimal valorGasto;
    private BigDecimal valorRestante;
    private BigDecimal progresso;
    private Integer mes;
    private Integer ano;
    private CategoriaDTO categoriaDTO;
    private NotificacaoOrcamentoDTO notificacao;

    public OrcamentoDTO() {
    }

    public OrcamentoDTO(Long id, String descricao, BigDecimal valorEstimado, BigDecimal valorGasto, BigDecimal valorRestante, BigDecimal progresso,
                        Integer mes, Integer ano, CategoriaDTO categoriaDTO, NotificacaoOrcamento notificacao) {
        this.id = id;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.valorGasto = valorGasto;
        this.valorRestante = valorRestante;
        this.progresso = progresso;
        this.mes = mes;
        this.ano = ano;
        this.categoriaDTO = categoriaDTO;
        this.setNotificacao(notificacao);
    }

    public OrcamentoDTO(Orcamento orcamento) {
        this.id = orcamento.getId();
        this.descricao = orcamento.getDescricao();
        this.valorEstimado = orcamento.getValorEstimado();
        this.valorGasto = orcamento.getValorGasto();
        this.valorRestante = orcamento.getValorRestante();
        this.progresso = orcamento.getProgresso();
        this.mes = orcamento.getMes();
        this.ano = orcamento.getAno();
        this.categoriaDTO = new CategoriaDTO(orcamento.getCategoria());
        this.setNotificacao(orcamento.getNotificacao());
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao() {
        this.descricao = categoriaDTO.getNome();
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public BigDecimal getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(BigDecimal valorGasto) {
        this.valorGasto = valorGasto;
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public BigDecimal getProgresso() {
        return progresso;
    }

    public void setProgresso(BigDecimal progresso) {
        this.progresso = progresso;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }

    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
        this.setDescricao();
    }

    public NotificacaoOrcamentoDTO getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(NotificacaoOrcamento notificacao) {
        if (notificacao != null) {
            this.notificacao = new NotificacaoOrcamentoDTO(notificacao);
        } else {
            this.notificacao = new NotificacaoOrcamentoDTO();
        }
    }
}
