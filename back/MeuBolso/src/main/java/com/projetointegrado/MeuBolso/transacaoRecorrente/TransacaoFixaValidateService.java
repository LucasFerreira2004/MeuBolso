package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoFixaValidateService {
    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public TransacaoRecorrente validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;

        return transacao;
    }

}
