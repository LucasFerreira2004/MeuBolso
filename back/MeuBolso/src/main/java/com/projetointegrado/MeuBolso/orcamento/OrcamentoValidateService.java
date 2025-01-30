package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.orcamento.exception.CategoriaOrcamentoException;
import com.projetointegrado.MeuBolso.orcamento.exception.OrcamentoDuplicadoException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    // Valida se existe uma categoria com a mesma categoria para o mesmo periodo
    @Transactional
    public void validateSamePeriod(Categoria categoria, Usuario usuario, Integer mes, Integer ano, OrcamentoDuplicadoException orcamentoDuplicadoException, CategoriaOrcamentoException categoriaOrcamentoException) {
        if (categoria.getTipo() != TipoCategoria.DESPESA)
            throw categoriaOrcamentoException;

        Orcamento orcamento = orcamentoRepository.findOrcamentoByCategoriaAndUsuario(categoria.getId(), usuario.getId()).orElse(null);

        if (orcamento != null && Objects.equals(orcamento.getMes(), mes) && Objects.equals(orcamento.getAno(), ano))
            throw orcamentoDuplicadoException;
    }
}
