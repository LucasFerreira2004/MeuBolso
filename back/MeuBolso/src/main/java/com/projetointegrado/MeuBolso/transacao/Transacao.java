package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrente;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser no mínimo 0.01")
    private BigDecimal valor;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate data;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_origem")
    private Conta conta;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT") //especificar tamanho maximo da descricao
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = true)
    @JoinColumn(name = "transacao_fixa_id")
    private TransacaoRecorrente transacaoRecorrente;

    private Enum

    public Transacao(Long id, BigDecimal valor, LocalDate data, TipoTransacao tipo, Categoria categoria, Conta conta, String comentario, String descricao, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
        this.conta = conta;
        this.comentario = comentario;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Transacao (TransacaoRecorrente transacaoRecorrente, LocalDate data) {
        this.id = null;
        if(transacaoRecorrente.getQtdParcelas() != null) {
            BigDecimal qtdParcelas = new BigDecimal(transacaoRecorrente.getQtdParcelas());
            this.valor = transacaoRecorrente.getValor()
                    .divide(qtdParcelas, 2, RoundingMode.DOWN);
        }else{
            this.valor = transacaoRecorrente.getValor();
        }
        this.data = data;
        this.tipo = transacaoRecorrente.getTipo();
        this.categoria = transacaoRecorrente.getCategoria();
        this.conta = transacaoRecorrente.getConta();
        this.comentario = null;
        this.descricao = transacaoRecorrente.getDescricao();
        this.usuario = transacaoRecorrente.getUsuario();
        this.transacaoRecorrente = transacaoRecorrente;
    }

    public Transacao() {
    }

    // Getters e Setters


    public TransacaoRecorrente getTransacaoFixa() {
        return transacaoRecorrente;
    }

    public void setTransacaoFixa(TransacaoRecorrente transacaoRecorrente) {
        this.transacaoRecorrente = transacaoRecorrente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data_transacao) {
        this.data = data_transacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
