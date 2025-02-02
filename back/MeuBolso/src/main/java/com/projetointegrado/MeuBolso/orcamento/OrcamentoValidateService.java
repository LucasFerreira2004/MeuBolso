package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.orcamento.exception.CategoriaOrcamentoException;
import com.projetointegrado.MeuBolso.orcamento.exception.OrcamentoDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    // Se a categoria não for do tipo DESPESA, lança outra exceção (regra de negócio)
    public void validateCategoria(Categoria categoria, CategoriaOrcamentoException categoriaOrcamentoException) {
        if (categoria.getTipo() != TipoCategoria.DESPESA)
            throw categoriaOrcamentoException;
    }

    // Verifica se houve duplicidade de orçamentos para o mesmo usuário, categoria e período
    public void validateSamePeriod(Categoria categoria, String usuarioId, Integer mes, Integer ano, Long idAtual,
                                   OrcamentoDuplicadoException orcamentoDuplicadoException) {
        // Busca um orçamento existente para o mesmo usuário, categoria e período
        Optional<Orcamento> orcamento = orcamentoRepository.findByCategoriaUsuarioAndPeriodo(categoria.getId(), usuarioId, ano, mes);

        // Se existir e for um registro diferente (ou seja, id diferente ou sendo um novo registro)
        if (orcamento.isPresent() && !orcamento.get().getId().equals(idAtual)) {
            throw orcamentoDuplicadoException;
        }
    }
}
