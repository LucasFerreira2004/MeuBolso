package com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto;

import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoFixaSaveDTO {
    @NotNull(message = "valor não pde ser nulo, deve ser do tipo BigDecimal. ex: 9.99")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    private BigDecimal valor;

    @NotNull(message = "O tipo de transação é obrigatório. tipos permitidos: RECEITA ou DESPESA")
    @ValidEnum(value = TipoTransacao.class, message = "tipos permitidos são DESPESA e RECEITA")
    private String tipoTransacao;

    @NotNull(message = "data não pose der nulo, deve ser do tipo Date. ex: \"2025-12-25\"")
    private Date data;

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

    public TransacaoFixaSaveDTO(BigDecimal valor, String tipoTransacao, Date data, Long contaId, Long categoriaId, String descricao) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.data = data;
        this.contaId = contaId;
        this.categoriaId = categoriaId;
        this.descricao = descricao;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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
}
