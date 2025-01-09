package com.projetointegrado.MeuBolso.transacao;

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
    public TransacaoDTO findById(Long id){
        Transacao transacao = transacaoRepository.findById(id).get(); //mudar essa linha pra colocar o .orElse(null)
        return new TransacaoDTO(transacao);
    }
    @Transactional(readOnly = true)
    public List<TransacaoDTO> findAll(){
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream().map(TransacaoDTO::new).toList();
    }

}
