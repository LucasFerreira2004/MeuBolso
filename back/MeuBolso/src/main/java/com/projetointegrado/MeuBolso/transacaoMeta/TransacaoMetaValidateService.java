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
    private MetaRepository metaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Meta meta = metaRepository.findById(id).orElseThrow(() -> entidadeNaoEncontrada);
        if (!meta.getUsuario().getId().equals(userId))
            throw acessoNegadoException;
    }

    public Meta validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!meta.getUsuario().getId().equals(userId))
            throw acessoNegadoException;

        return meta;
    }
}
