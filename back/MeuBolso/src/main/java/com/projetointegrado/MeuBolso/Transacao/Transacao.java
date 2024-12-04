package com.projetointegrado.MeuBolso.Transacao;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal valor;

    private Boolean e_fixo;

    private Integer qtd_repeticao;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Date data_transacao;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
