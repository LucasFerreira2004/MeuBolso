package com.projetointegrado.MeuBolso.transacao.dto;

import com.projetointegrado.MeuBolso.globalConstraints.NotBlankIfPresent.NotBlankIfPresent;
import com.projetointegrado.MeuBolso.globalConstraints.validEnum.ValidEnum;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;

import com.projetointegrado.MeuBolso.transacao.exceptions.TipoTransacaoNaoIdentificadoException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoSaveDTO {

    @NotNull(message = "valor não pde ser nulo, deve ser do tipo BigDecimal. ex: 9.99")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    private BigDecimal valor;

    private LocalDate data;

    @NotNull(message = "O tipo de transação é obrigatório. tipos permitidos: RECEITA ou DESPESA")
    @ValidEnum(value = TipoTransacao.class, message = "tipos permitidos são DESPESA e RECEITA")
    private String tipoTransacao; //está em string apenas para poder verificar o erro de TipoTransacao

    private final String categoriaIdDefaultMessage = "O categoriaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = categoriaIdDefaultMessage)
    @Positive(message = categoriaIdDefaultMessage)
    private Long categoriaId;

    private final String contaIdDefaultMessage = "O contaId é obrigatório e deve ser um inteiro maior que 0";
    @NotNull(message = contaIdDefaultMessage)
    @Positive(message = contaIdDefaultMessage)
    private Long contaId;

    @NotBlankIfPresent(message = "o comentario é opcional (pode receber valor null), mas caso atribuído com string deve receber uma não vazia.")
    private String comentario;

    private final String descricaoDefalutMessage = "A descrição é obrigatória e deve ser uma string válida.";
    @NotNull(message = descricaoDefalutMessage)
    @NotBlank(message = descricaoDefalutMessage)
    private String descricao;

    public TransacaoSaveDTO(BigDecimal valor, LocalDate data, String tipoTransacao, Long categoriaId, Long contaId, String descricao) {
        this.valor = valor;
        this.data = data;
        this.tipoTransacao = tipoTransacao;
        this.categoriaId = categoriaId;
        this.contaId = contaId;
        this.descricao = descricao;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "TransacaoSaveDTO{" +
                "valor=" + valor +
                ", data=" + data +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", categoriaIdDefaultMessage='" + categoriaIdDefaultMessage + '\'' +
                ", categoriaId=" + categoriaId +
                ", contaIdDefaultMessage='" + contaIdDefaultMessage + '\'' +
                ", contaId=" + contaId +
                ", comentario='" + comentario + '\'' +
                ", descricaoDefalutMessage='" + descricaoDefalutMessage + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
