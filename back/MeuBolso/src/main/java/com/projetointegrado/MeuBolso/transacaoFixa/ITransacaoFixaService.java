package com.projetointegrado.MeuBolso.transacaoFixa;

import com.projetointegrado.MeuBolso.transacaoFixa.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacaoFixa.dto.TransacaoFixaSaveDTO;

import java.util.List;

public interface ITransacaoFixaService {
    public List<TransacaoFixaDTO> findAll(String userId);
    public TransacaoFixaDTO findById(String userId, Long id);
    public TransacaoFixaDTO save(String userId, TransacaoFixaSaveDTO dto);
    public TransacaoFixaDTO update(String userId, Long id, TransacaoFixaSaveDTO dto);
    public TransacaoFixaDTO delete(String userId, Long id);
}
