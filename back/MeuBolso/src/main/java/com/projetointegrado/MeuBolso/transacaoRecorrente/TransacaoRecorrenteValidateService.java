package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.categoria.CategoriaService;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.exceptions.MismatchTransacaoTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoRecorrenteValidateService {
    @Autowired
    private TransacaoRecorrenteRepository transacaoRecorrenteRepository;

    @Autowired
    private CategoriaService categoriaService;

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

    public void validateTipo(String userId, TipoTransacao tipoTransacao, Long categoriaId) {
        CategoriaDTO categoriaDTO = categoriaService.findById(userId, categoriaId);
        TipoTransacao tipoDaCategoria = TipoTransacao.valueOf(categoriaDTO.getTipo().name());
        if (tipoDaCategoria != tipoTransacao)
            throw new MismatchTransacaoTypeException("categoriaId", "o tipo da categoria não corresponde ao tipo da transacao");
    }
}
