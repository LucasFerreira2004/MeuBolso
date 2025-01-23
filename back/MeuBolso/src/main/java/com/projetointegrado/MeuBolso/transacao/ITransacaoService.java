package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;

import java.util.List;

public interface ITransacaoService {
    public TransacaoDTO findById(String userId, Long id);

    public List<TransacaoDTO> findAll(String userId);

    public TransacaoDTO save(String userId, TransacaoSaveDTO dto);
    public TransacaoDTO update(String userId, Long id, TransacaoSaveDTO dto);
    public TransacaoDTO delete(String userId, Long id);
}
