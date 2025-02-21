package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.categoria.CategoriaService;
import com.projetointegrado.MeuBolso.categoria.ICategoriaService;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.conta.Conta;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.exceptions.MismatchTransacaoTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoValidateService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ICategoriaService categoriaService;

    public void validate(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> entidadeNaoEncontrada);
        if (!transacao.getUsuario().getId().equals(userId))
            throw  acessoNegadoException;
    }
    public Transacao validateAndGet(Long id, String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada, AcessoNegadoException acessoNegadoException) {
        Transacao transacao = transacaoRepository.findById(id)
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
