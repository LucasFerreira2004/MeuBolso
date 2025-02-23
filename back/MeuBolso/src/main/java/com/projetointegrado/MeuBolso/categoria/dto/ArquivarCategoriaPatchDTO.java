package com.projetointegrado.MeuBolso.categoria.dto;

import jakarta.validation.constraints.NotNull;

public record ArquivarCategoriaPatchDTO (
        @NotNull(message = "o campo ativa é obrigatório")
        Boolean ativa
){}
