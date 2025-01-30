package com.projetointegrado.MeuBolso.orcamento.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrcamentoPostDTO {
    @NotNull(message = "O valor estimado deve ser informado")
    @DecimalMin(value = "0.01", message = "O valor estimado deve ser maior que zero")
    private BigDecimal valorEstimado;

    private LocalDate periodo;

    @NotNull(message = "A categoria selecionada deve ser informada")
    @Min(value = 17, message = "A categoria selecionada deve ser válida")
    private Long idCategoria;

    public OrcamentoPostDTO(BigDecimal valorEstimado, LocalDate periodo, Long idCategoria) {
        this.valorEstimado = valorEstimado;
        this.periodo = periodo;
        this.idCategoria = idCategoria;
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public LocalDate getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalDate periodo) {
        this.periodo = periodo;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
}
