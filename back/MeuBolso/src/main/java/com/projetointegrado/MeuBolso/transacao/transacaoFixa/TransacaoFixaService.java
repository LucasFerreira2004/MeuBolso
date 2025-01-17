package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

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
        return transacaoFixaRepository.findAllByUsuario(userId).stream().map(x -> new TransacaoFixaDTO(x));
    }
}
