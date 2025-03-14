package com.projetointegrado.MeuBolso.transacao;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoSaveDTO;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacao.dto.SumTransacoesDTO;

import java.time.LocalDate;
import java.util.List;

public interface ITransacaoService {
    public TransacaoDTO findById(String userId, Long id);
    public List<TransacaoDTO> findAllInRangeByMonth(String userId, LocalDate data);
    public SumTransacoesDTO findSumDespesasInRangeByMonth(String userId, LocalDate data);
    public SumTransacoesDTO findSumReceitasInRangeByMonth(String userId, LocalDate data);
    public TransacaoDTO save(String userId, TransacaoSaveDTO dto);
    public TransacaoDTO update(String userId, Long id, TransacaoSaveDTO dto);
    public TransacaoDTO delete(String userId, Long id);
}
