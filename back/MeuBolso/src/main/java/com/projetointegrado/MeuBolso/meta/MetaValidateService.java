package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.exception.DescricaoUnicaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetaValidateService {
    @Autowired
    private MetaRepository metaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!meta.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }

    public Meta validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!meta.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;

        return meta;
    }

    public void validateDescricaoUnica(String descricao, String usuarioId, Long id, DescricaoUnicaException descricaoUnicaException) {
        Optional<Meta> metaExistente = metaRepository.findByDescricaoAndUsuarioId(descricao, usuarioId);
        if (metaExistente.isPresent()) {
            if (id == null || !metaExistente.get().getId().equals(id)) {
                throw descricaoUnicaException;
            }
        }
    }
}