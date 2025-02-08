package com.projetointegrado.MeuBolso.transacaoMeta;

import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import com.projetointegrado.MeuBolso.transacaoMeta.dto.TransacaoMetaSaveDTO;

public interface ITransacaoMetaService {
    TransacaoDTO save(String userId, TransacaoMetaSaveDTO dto, Long metaId);
}
