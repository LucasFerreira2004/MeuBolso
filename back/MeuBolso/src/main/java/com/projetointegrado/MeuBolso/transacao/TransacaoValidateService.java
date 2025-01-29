package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoValidateService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public Transacao validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;

        return transacao;
    }
}
