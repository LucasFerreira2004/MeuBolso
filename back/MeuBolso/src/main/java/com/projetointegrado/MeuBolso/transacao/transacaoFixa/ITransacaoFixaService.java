package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import com.projetointegrado.MeuBolso.transacao.transacaoFixa.dto.TransacaoFixaDTO;

import java.util.List;

public interface ITransacaoFixaService {
    public List<TransacaoFixaDTO> findAll(String userId);
    public TransacaoFixaDTO findById(String userId, Long id);
}
