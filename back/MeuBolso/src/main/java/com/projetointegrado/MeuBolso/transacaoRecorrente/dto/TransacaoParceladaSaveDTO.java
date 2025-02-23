package com.projetointegrado.MeuBolso.transacaoRecorrente.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetointegrado.MeuBolso.globalConstraints.NotBlankIfPresent.NotBlankIfPresent;
import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacaoRecorrente.Periodicidade;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TipoRepeticao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoParceladaSaveDTO(
    @NotNull(message = "valor não pde ser nulo, deve ser do tipo BigDecimal. ex: 9.99")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    BigDecimal valor,

    @NotNull(message = "O tipo de transação é obrigatório. tipos permitidos: RECEITA ou DESPESA")
    @ValidEnum(value = TipoTransacao.class, message = "tipos permitidos são DESPESA e RECEITA")
    String tipoTransacao,

    LocalDate data,

    @NotNull(message =  "O contaId é obrigatório e deve ser um inteiro maior que 0")
    @Positive(message =  "O contaId é obrigatório e deve ser um inteiro maior que 0")
    Long contaId,

    @NotNull(message = "O categoriaId é obrigatório e deve ser um inteiro maior que 0")
    @Positive(message = "O categoriaId é obrigatório e deve ser um inteiro maior que 0")
    Long categoriaId,

    @NotNull(message = "a descricao e obrigatoria e deve ser uma string valida.")
    @NotBlank(message = "a descricao e obrigatoria e deve ser uma string valida.")
    String descricao,

    @NotBlankIfPresent(message = "o comentario é opcional (pode receber valor null), mas caso atribuído com string deve receber uma não vazia.")
    String comentario,

    @NotNull(message ="a periodicidade é obrigatória e deve ter o valor: DIARIO, SEMANAL ou MENSAL")
    @ValidEnum(value = Periodicidade.class, message = "tipos permitidos são DIARIO, SEMANAL ou MENSAL" )
    String periodicidade,

    @NotNull(message = "a quantidade de parcelas não pode ser nula")
    @Min(value = 2, message = "a quantidade mínima de parcelas é 2")
    Integer qtdParcelas,

    @JsonIgnore
    TipoRepeticao tipoRepeticao
    ) implements ITransacaoRecorrenteDTO {
    public TipoRepeticao tipoRepeticao(){
        return TipoRepeticao.PARCELAMENTO;
    }
}
