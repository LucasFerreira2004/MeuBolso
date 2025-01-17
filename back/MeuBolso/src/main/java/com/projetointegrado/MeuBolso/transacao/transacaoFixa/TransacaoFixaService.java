package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoFixaService implements ITransacaoFixaService {
    @Autowired
    private TransacaoFixaRepository transacaoFixaRepository;


}
