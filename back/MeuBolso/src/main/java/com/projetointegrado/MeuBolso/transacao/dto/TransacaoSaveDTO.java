package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoSaveDTO {
    private BigDecimal valor;
    private Date dataTransacao;
    @NotNull(message = "O tipo de transação é obrigatório.")
    private String tipoTransacao; //está em string apenas para poder verificar o erro de TipoTransacao
    private Long categoriaId;
    private Long contaId;
    private String comentario;
    private String descricao;
}
