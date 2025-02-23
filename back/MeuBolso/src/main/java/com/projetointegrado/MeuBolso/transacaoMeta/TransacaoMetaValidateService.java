package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.Meta;
import com.projetointegrado.MeuBolso.meta.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoMetaValidateService {
    @Autowired
    private TransacaoMetaRepository transacaoMeta;
    @Autowired
    private TransacaoMetaRepository transacaoMetaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoMeta transacaoMeta = transacaoMetaRepository.findById(id).orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacaoMeta.getMeta().getUsuario().getId().equals(userId))
            throw acessoNegadoException;
    }

    public TransacaoMeta validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        TransacaoMeta transacaoMeta = transacaoMetaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacaoMeta.getMeta().getUsuario().getId().equals(userId))
            throw acessoNegadoException;

        return transacaoMeta;
    }
}
