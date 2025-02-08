package com.projetointegrado.MeuBolso.transacaoMeta;

public interface ITransacaoMetaService {
    TransacaoMeta criarTransacaoMeta(String userId, Long idMeta, TransacaoMetaSaveDTO dto);

}
