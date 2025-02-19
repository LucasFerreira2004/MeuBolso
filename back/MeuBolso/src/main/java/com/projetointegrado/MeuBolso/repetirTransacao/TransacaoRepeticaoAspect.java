package com.projetointegrado.MeuBolso.repetirTransacao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Aspect
@Component
public class TransacaoRepeticaoAspect {
    private final TransacaoRepeticaoService transacaoRepeticaoService;

    public TransacaoRepeticaoAspect(TransacaoRepeticaoService transacaoRepeticaoService) {
        this.transacaoRepeticaoService = transacaoRepeticaoService;
    }

    @Before("execution(* com.projetointegrado.MeuBolso.conta.ContaService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findAll*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findSum*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.CategoriaDashboardService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.ContaDashboardService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.TransacoesDashboardsService.find*(..))")
    public synchronized void gerarTransacoesRecorrentes(JoinPoint joinPoint) {
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
            System.out.println("Aspect -> Gerando transações fixas para usuário ID " + usuarioId + " e data " + data);
            transacaoRepeticaoService.gerarTransacoes(data, usuarioId);
        } else {
            System.out.println("Aspect -> Parâmetros não encontrados, pulando geração de transações.");
        }
    }
}
