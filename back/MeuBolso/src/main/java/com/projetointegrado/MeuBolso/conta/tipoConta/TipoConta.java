package com.projetointegrado.MeuBolso.conta.tipoConta;

import jakarta.persistence.*;

@Entity
public class TipoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EnumTipoConta tipoConta;

    public TipoConta(Long id, EnumTipoConta tipoConta) {
        this.id = id;
        this.tipoConta = tipoConta;
    }
    public TipoConta() {}

    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(EnumTipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
