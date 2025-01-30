package com.projetointegrado.MeuBolso.orcamento.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrcamentoPostDTO {
    @NotNull(message = "O valor estimado deve ser informado")
    @DecimalMin(value = "0.01", message = "O valor estimado deve ser maior que zero")
    private BigDecimal valorEstimado;

    @NotNull(message = "O mês deve ser informado")
    @Min(value = 1, message = "O mês deve ser válido (1-12)")
    @Max(value = 12, message = "O mês deve ser válido (1-12)")
    private Integer mes;

    @NotNull(message = "O ano deve ser informado")
    @Min(value = 2000, message = "O ano deve ser válido (>= 2000)")
    private Integer ano;

    @NotNull(message = "A categoria selecionada deve ser informada")
    @Min(value = 17, message = "A categoria selecionada deve ser válida")
    private Long idCategoria;

    public OrcamentoPostDTO(BigDecimal valorEstimado, int mes, int ano, Long idCategoria) {
        this.valorEstimado = valorEstimado;
        this.mes = mes;
        this.ano = ano;
        this.idCategoria = idCategoria;
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
