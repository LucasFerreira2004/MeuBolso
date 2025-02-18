package com.projetointegrado.MeuBolso.repetirTransacao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class TransacaoRepeticaoAspect {
    private final TransacaoRepeticaoExecutor transacaoRepeticaoExecutor;
    private final Map<String, Instant> ultimaExecucao = new ConcurrentHashMap<>();

    public TransacaoRepeticaoAspect(TransacaoRepeticaoExecutor transacaoRepeticaoExecutor) {
        this.transacaoRepeticaoExecutor = transacaoRepeticaoExecutor;
    }

    @Before("execution(* com.projetointegrado.MeuBolso.conta.ContaService.find*(..)) || execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findAll*(..)) " +
            "|| execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findSum*(..)) || execution(* com.projetointegrado.MeuBolso.dashboard.CategoriaDashboardService.find*(..))" +
            "|| execution(* com.projetointegrado.MeuBolso.dashboard.ContaDashboardService.find*(..)) || execution(* com.projetointegrado.MeuBolso.dashboard.TransacoesDashboardsService.find*(..))")
    public void gerarTransacoesRecorrentes(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        LocalDate data = null;
        String usuarioId = null;

        for (Object arg : args) {
            if (arg instanceof LocalDate) {
                data = (LocalDate) arg;
            } else if (arg instanceof String) {
                usuarioId = (String) arg;
            }
        }

        if (data != null && usuarioId != null) {
            Instant agora = Instant.now();
            Instant ultima = ultimaExecucao.get(usuarioId);

            // Evita execuções muito frequentes (por exemplo, dentro de 1 segundos)
            if (ultima != null && agora.minusSeconds(1).isBefore(ultima)) {
                System.out.println("AOP -> Ignorando geração de transações para " + usuarioId + ", já foi feita recentemente.");
                return;
            }

            System.out.println("AOP -> Gerando transações fixas para usuário ID " + usuarioId + " e data " + data);
            transacaoRepeticaoExecutor.executarGeracaoTransacoes(data, usuarioId);
        } else {
            System.out.println("AOP -> Parâmetros não encontrados, pulando geração de transações.");
        }
    }
}
