package com.projetointegrado.MeuBolso.transacaoFixa;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
public class TransacaoRepeticaoExecutor {

    private final TransacaoRepeticaoService transacaoRepeticaoService;

    public TransacaoRepeticaoExecutor(TransacaoRepeticaoService transacaoRepeticaoService) {
        this.transacaoRepeticaoService = transacaoRepeticaoService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executarGeracaoTransacoes(LocalDate data, String usuarioId) {
        transacaoRepeticaoService.gerarTransacoes(data, usuarioId);
    }
}

