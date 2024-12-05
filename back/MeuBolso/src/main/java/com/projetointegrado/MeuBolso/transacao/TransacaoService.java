package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional(readOnly = true)
    public TransacaoDTO findTransacaoById(Long id){
        Transacao transacao = transacaoRepository.findById(id).get(); //mudar essa linha pra colocar o .orElse(null)
        return new TransacaoDTO(transacao);
    }
    @Transactional(readOnly = true)
    public List<TransacaoDTO> findTransacoes(){
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream().map(TransacaoDTO::new).toList();
    }

}
