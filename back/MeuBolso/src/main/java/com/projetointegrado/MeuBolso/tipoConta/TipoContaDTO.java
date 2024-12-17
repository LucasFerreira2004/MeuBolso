package com.projetointegrado.MeuBolso.tipoConta;

public class TipoContaDTO {
    private Long id;
    private EnumTipoConta tipoConta;


    public TipoContaDTO(TipoConta tipoConta) {
        this.id = tipoConta.getId();
        this.tipoConta = tipoConta.getTipoConta();
    }

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
