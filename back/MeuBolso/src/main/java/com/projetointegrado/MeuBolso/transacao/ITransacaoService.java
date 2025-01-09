package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;

import java.util.List;

public interface ITransacaoService {
    public TransacaoDTO findById(Long id);
    public List<TransacaoDTO> findAll();
}
