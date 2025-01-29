package com.projetointegrado.MeuBolso.transacaoFixa.dto;

import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoFixa.Periodicidade;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoFixaSaveDTO {
    @NotNull(message = "valor não pde ser nulo, deve ser do tipo BigDecimal. ex: 9.99")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    private BigDecimal valor;

    @NotNull(message = "O tipo de transação é obrigatório. tipos permitidos: RECEITA ou DESPESA")
    @ValidEnum(value = TipoTransacao.class, message = "tipos permitidos são DESPESA e RECEITA")
    private String tipoTransacao;

    private LocalDate data;

    private final String contaIdDefaultMessage = "O contaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = contaIdDefaultMessage)
    @Positive(message = contaIdDefaultMessage)
    private Long contaId;

    private final String categoriaIdDefaultMessage = "O categoriaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = categoriaIdDefaultMessage)
    @Positive(message = categoriaIdDefaultMessage)
    private Long categoriaId;

    private final String descricaoDefaultMessage = "a descricao e obrigatoria e deve ser uma string valida.";
    @NotNull(message = descricaoDefaultMessage)
    @NotBlank(message = descricaoDefaultMessage)
    private String descricao;

//    private final String comentarioDefaultMessage = "o comentario deve ser uma string valida.";
//    @NotNull(message = comentarioDefaultMessage)
//    private String comentario; TRATAR A CRIACAO DE COMENTARIO E CRIAR NOTATION PARA NULO OU STRING VALIDA

    private final String periodicidadeDefaultMessage = "a periodicidade é obrigatória e deve ter o valor: DIARIO, SEMANAL ou MENSAL";
    @NotNull(message = periodicidadeDefaultMessage)
    @ValidEnum(value = Periodicidade.class, message = "tipos permitidos são DIARIO, SEMANAL ou MENSAL" )
    private String periodicidade;

    public TransacaoFixaSaveDTO(BigDecimal valor, String tipoTransacao, LocalDate data, Long contaId, Long categoriaId, String descricao, String periodicidade) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.data = data;
        this.contaId = contaId;
        this.categoriaId = categoriaId;
        this.descricao = descricao;
        this.periodicidade = periodicidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }
}
