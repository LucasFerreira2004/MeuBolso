package com.projetointegrado.MeuBolso.Transacao;

import com.projetointegrado.MeuBolso.Transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional(readOnly = true)
    public TransacaoDTO findTransacaoById(Long id){
        Transacao transacao = transacaoRepository.findById(id).get();
        return new TransacaoDTO(transacao);
    }

}
