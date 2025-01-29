package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoFixaValidateService {
    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoFixa transacao = transacaoFixaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public TransacaoFixa validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoFixa transacao = transacaoFixaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;

        return transacao;
    }

}
