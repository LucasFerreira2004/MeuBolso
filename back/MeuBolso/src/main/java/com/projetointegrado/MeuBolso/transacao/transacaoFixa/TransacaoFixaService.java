package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoFixaService implements ITransacaoFixaService {
    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;

    @Transactional
    public List<TransacaoFixaDTO> findAll(String userId){
        return transacaoFixaRepository.findAllByUsuario(userId).stream().map(x -> new TransacaoFixaDTO(x)).toList();
    }

    @Transactional
    public TransacaoFixaDTO findById(String userId, Long id){
        TransacaoFixa transacao = transacaoFixaRepository.findById(id).orElse(null);
        if (transacao == null)
            throw new EntidadeNaoEncontradaException("/{id}", "TransacaoFixa nao encontrada");
        if (!transacao.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();
        return new TransacaoFixaDTO(transacao);
    }
}
