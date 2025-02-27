package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;

import java.util.List;

public interface ITransacaoMetaService {
    public List<TransacaoMetaDTO> findAll(String userId);
    public TransacaoMetaDTO findById(String userId, Long idTransacao);
    public TransacaoMetaDTO update(String userId, Long idTransacao, TransacaoMetaSaveDTO dto);
    public TransacaoMetaDTO save(String userId, TransacaoMetaSaveDTO dto);
    public List<TransacaoMetaDTO> findByMeta(Long metaId);
    public TransacaoMetaDTO delete(String userId, Long idTransacao);
}
