package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;

import java.util.List;

public interface ITransacaoService {
    public TransacaoDTO findById(String userId, Long id);

    public List<TransacaoDTO> findAll(String userId);

    public TransacaoDTO save(TransacaoSaveDTO dto);
}
