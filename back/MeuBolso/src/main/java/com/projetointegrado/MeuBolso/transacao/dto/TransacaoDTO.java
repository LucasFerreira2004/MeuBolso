package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacaoMeta.TransacaoMeta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate data_transacao;
    private TipoTransacao tipo;
    private CategoriaDTO categoriaDTO;
    private ContaDTO contaDTO;
    private String comentario;
    private String descricao;
    private String origem;
    private Long idTransacaoRecorrente;

    public TransacaoDTO(Transacao transacao) {
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.data_transacao = transacao.getData();
        this.tipo = transacao.getTipo();
        this.categoriaDTO = new CategoriaDTO(transacao.getCategoria());
        this.contaDTO = new ContaDTO(transacao.getConta());
        this.comentario = transacao.getComentario();
        this.descricao = transacao.getDescricao();
        this.origem = transacao.getOrigemTransacao().name();
        if (transacao.getTransacaoRecorrente() != null)
            this.idTransacaoRecorrente = transacao.getTransacaoRecorrente().getId();
    }

    public TransacaoDTO(TransacaoMeta transacao) {
        //isso so funcionará se os atributos forem os mesmos e estiverem na mesma ordem que a entidade
        this.id = transacao.getId();
        this.valor = transacao.getTransacao().getValor();
        this.data_transacao = transacao.getTransacao().getData();
        this.tipo = transacao.getTransacao().getTipo();
        this.categoriaDTO = new CategoriaDTO(transacao.getTransacao().getCategoria());
        this.contaDTO = new ContaDTO(transacao.getTransacao().getConta());
        this.comentario = transacao.getTransacao().getComentario();
        this.descricao = transacao.getTransacao().getDescricao();
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

    public LocalDate getData_transacao() {
        return data_transacao;
    }

    public void setData_transacao(LocalDate data_transacao) {
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

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public Long getIdTransacaoRecorrente() {
        return idTransacaoRecorrente;
    }

    public void setIdTransacaoRecorrente(Long idTransacaoRecorrente) {
        this.idTransacaoRecorrente = idTransacaoRecorrente;
    }

    @Override
    public String toString() {
        return "TransacaoDTO{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_transacao=" + data_transacao +
                ", tipo=" + tipo +
                ", categoriaDTO=" + categoriaDTO +
                ", contaDTO=" + contaDTO +
                ", comentario='" + comentario + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
