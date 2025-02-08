package com.projetointegrado.MeuBolso.transacaoMeta.dto;

import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

import com.projetointegrado.MeuBolso.transacao.exceptions.TipoTransacaoNaoIdentificadoException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoMetaSaveDTO {

    @NotNull(message = "valor não pde ser nulo, deve ser do tipo BigDecimal. ex: 9.99")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    private BigDecimal valor;

    private LocalDate data;

    @NotNull(message = "O tipo de transação é obrigatório. tipos permitidos: RECEITA ou DESPESA")
    @ValidEnum(value = TipoTransacao.class, message = "tipos permitidos são DESPESA e RECEITA")
    private String tipoTransacao;

    private final String contaIdDefaultMessage = "O contaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = contaIdDefaultMessage)
    @Positive(message = contaIdDefaultMessage)
    private Long contaId;

    private final String categoriaIdDefaultMessage = "O id da categoria é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = categoriaIdDefaultMessage)
    @Positive(message = categoriaIdDefaultMessage)
    private Long categoriaId;

    public TransacaoMetaSaveDTO(BigDecimal valor, LocalDate data, String tipoTransacao, Long contaId, Long categoriaId) {
        this.valor = valor;
        this.data = data;
        this.tipoTransacao = tipoTransacao;
        this.contaId = contaId;
        this.categoriaId = categoriaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        if (data == null) {
            throw new TipoTransacaoNaoIdentificadoException(); //criar exceção depois
        }
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoTransacao getTipoTransacao() {
        return TipoTransacao.valueOf(tipoTransacao);
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
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

    @Override
    public String toString() {
        return "TransacaoMetaSaveDTO{" +
                "valor=" + valor +
                ", data=" + data +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", contaId='" + contaId + '\'' +
                ", contaId=" + contaId +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
