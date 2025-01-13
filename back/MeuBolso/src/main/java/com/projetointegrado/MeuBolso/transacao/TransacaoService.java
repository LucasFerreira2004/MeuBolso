package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService implements ITransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional(readOnly = true)
    public TransacaoDTO findById(String userId, Long id){
        Transacao transacao = transacaoRepository.findById(id).orElse(null);
        if (transacao == null)
            throw new EntidadeNaoEncontradaException("/{id}", "Transacao nao encontrada");
        if (!transacao.getUsuario().getId().equals(userId))
            throw new AcessoNegadoException();
        return new TransacaoDTO(transacao);
    }
    @Transactional(readOnly = true)
    public List<TransacaoDTO> findAll(String userId) {
        List<Transacao> transacoes = transacaoRepository.findAllByUsuario(userId);
        return transacoes.stream().map(TransacaoDTO::new).toList();
    }

    @Transactional
    public TransacaoDTO save(TransacaoSaveDTO dto) {

    }

}
