package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.banco.Banco;
import com.projetointegrado.MeuBolso.conta.dto.ContaDTO;
import com.projetointegrado.MeuBolso.tipoConta.TipoConta;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal saldo;
    @ManyToOne
    private TipoConta tipo_conta;
    @ManyToOne
    private Banco banco;
    @ManyToOne
    private Usuario usuario;

    public Conta(Long id, BigDecimal saldo, TipoConta tipo_conta, Banco banco, Usuario usuario) {
        this.id = id;
        this.saldo = saldo;
        this.tipo_conta = tipo_conta;
        this.banco = banco;
        this.usuario = usuario;
    }
    public Conta(ContaDTO contaDTO) {
        BeanUtils.copyProperties(contaDTO, this);
    }
    public Conta() {}

    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public TipoConta getTipo_conta() {
        return tipo_conta;
    }

    public void setTipo_conta(TipoConta tipo_conta) {
        this.tipo_conta = tipo_conta;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
