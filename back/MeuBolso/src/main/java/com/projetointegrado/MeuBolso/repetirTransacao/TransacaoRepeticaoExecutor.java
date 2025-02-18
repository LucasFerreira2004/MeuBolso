package com.projetointegrado.MeuBolso.repetirTransacao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

@Component
public class TransacaoRepeticaoExecutor {

    private final TransacaoRepeticaoService transacaoRepeticaoService;
    private final Set<String> usuariosEmExecucao = ConcurrentHashMap.newKeySet(); // Controle de execuções concorrentes

    public TransacaoRepeticaoExecutor(TransacaoRepeticaoService transacaoRepeticaoService) {
        this.transacaoRepeticaoService = transacaoRepeticaoService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executarGeracaoTransacoes(LocalDate data, String usuarioId) {
        // Se já está em execução para esse usuário, não faz nada
        if (!usuariosEmExecucao.add(usuarioId)) {
            System.out.println("Geração de transações já em andamento para o usuário " + usuarioId);
            return;
        }

        try {
            transacaoRepeticaoService.gerarTransacoes(data, usuarioId);
        } finally {
            usuariosEmExecucao.remove(usuarioId); // Libera após a execução
        }
    }
}


