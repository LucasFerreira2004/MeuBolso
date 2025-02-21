package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaValidateService {
    @Autowired
    private ContaRepository contaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!conta.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public Conta validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!conta.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
        return conta;
    }
}
