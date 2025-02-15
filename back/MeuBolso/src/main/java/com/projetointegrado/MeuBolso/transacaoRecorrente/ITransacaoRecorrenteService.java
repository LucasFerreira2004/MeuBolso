package com.projetointegrado.MeuBolso.transacaoRecorrente;

import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.TransacaoRecorrenteDTO;
import com.projetointegrado.MeuBolso.transacaoRecorrente.dto.ITransacaoRecorrenteDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITransacaoRecorrenteService {
    public List<TransacaoRecorrenteDTO> findAll(String userId);
    public List<TransacaoRecorrenteDTO> findAllArquivadas(String userId);
    public TransacaoRecorrenteDTO findById(String userId, Long id);
    public TransacaoRecorrenteDTO save(String userId, ITransacaoRecorrenteDTO dto);
    public TransacaoRecorrenteDTO update(String userId, Long id, ITransacaoRecorrenteDTO dto);
    public TransacaoRecorrenteDTO delete(String userId, Long id);
    public TransacaoRecorrenteDTO deleteAllAfterDate(String userId, Long id, LocalDate data);
    public TransacaoRecorrenteDTO atualizarStatusAtiva(String usuarioId, Long id, Boolean ativa);
}
