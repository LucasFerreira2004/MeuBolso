package com.projetointegrado.MeuBolso.conta.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaPutDTO extends ContaSaveDTO{
    @NotNull(message = "Data não pode ser vazia")
    private LocalDate data;

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
}
