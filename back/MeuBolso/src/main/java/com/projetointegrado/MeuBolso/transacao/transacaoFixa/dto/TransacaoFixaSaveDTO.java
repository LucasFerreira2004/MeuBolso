package com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto;

import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import jakarta.validation.constraints.DecimalMin;
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
    private Date dataCadastro;

    private final String contaIdDefaultMessage = "O contaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = contaIdDefaultMessage)
    @Positive(message = contaIdDefaultMessage)
    private Long idConta;

    public TransacaoFixaSaveDTO(BigDecimal valor, String tipoTransacao, Date dataCadastro, Long idConta) {
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataCadastro = dataCadastro;
        this.idConta = idConta;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }
}
