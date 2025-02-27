package com.projetointegrado.MeuBolso.transacaoMeta.dto;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoMeta.TransacaoMeta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoMetaDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate data;
    private TipoTransacao tipoTransacao;
    private ContaDTO conta;
    private CategoriaDTO categoria;
    private MetaDTO meta;

    public TransacaoMetaDTO() {
    }

    public TransacaoMetaDTO(Long id, BigDecimal valor, LocalDate data, TipoTransacao tipoTransacao, ContaDTO conta, CategoriaDTO categoria, MetaDTO meta) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.tipoTransacao = tipoTransacao;
        this.conta = conta;
        this.categoria = categoria;
        this.meta = meta;
    }

    public TransacaoMetaDTO(TransacaoMeta transacaoMeta) {
        this.id = transacaoMeta.getId();
        this.valor = transacaoMeta.getTransacao().getValor();
        this.data = transacaoMeta.getTransacao().getData();
        this.tipoTransacao = transacaoMeta.getTransacao().getTipo();
        this.conta = new ContaDTO(transacaoMeta.getTransacao().getConta());
        this.categoria = new CategoriaDTO(transacaoMeta.getTransacao().getCategoria());
        this.meta = new MetaDTO(transacaoMeta.getMeta());
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public ContaDTO getConta() {
        return conta;
    }

    public void setConta(ContaDTO conta) {
        this.conta = conta;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }
}
