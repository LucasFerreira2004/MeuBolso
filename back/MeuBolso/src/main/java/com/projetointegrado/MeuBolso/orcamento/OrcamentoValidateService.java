package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrcamentoValidateService {
    @Autowired
    private OrcamentoRepository orcamentoRepository;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> entidadeNaoEncontrada);
        if (!orcamento.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }

    public Orcamento validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!orcamento.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;

        return orcamento;
    }

    // Valida se existe um orcamento do usuario, com a mesma categoria e mesAno
//    public void validateOrcamentoUnico(Categoria categoria, String usuarioId, Long idOrcamento, YearMonth mesAno, RuntimeException e) {
//
//    }
}
