package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoFixaDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.ITransacaoRecorrenteDTO;

import java.util.List;

public interface ITransacaoRecorrenteService {
    public List<TransacaoFixaDTO> findAll(String userId);
    public TransacaoFixaDTO findById(String userId, Long id);
    public TransacaoFixaDTO save(String userId, ITransacaoRecorrenteDTO dto);
    public TransacaoFixaDTO update(String userId, Long id, ITransacaoRecorrenteDTO dto);
    public TransacaoFixaDTO delete(String userId, Long id);
}
