package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaValidateService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!categoria.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public Categoria validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!categoria.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
        return categoria;
    }
}
