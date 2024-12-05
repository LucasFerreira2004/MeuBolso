package com.projetointegrado.MeuBolso.conta.dto;

import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.conta.tipoConta.TipoConta;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ContaDTO {
    private Long id;
    private BigDecimal saldo;
    private String nome_banco;
    private TipoConta tipo_conta;

    public ContaDTO(Conta conta) {
        BeanUtils.copyProperties(conta, this);
    }
}
