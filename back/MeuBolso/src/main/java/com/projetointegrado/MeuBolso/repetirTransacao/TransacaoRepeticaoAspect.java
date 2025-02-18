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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class TransacaoRepeticaoAspect {
    private final TransacaoRepeticaoService transacaoRepeticaoService;
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public TransacaoRepeticaoAspect(TransacaoRepeticaoService transacaoRepeticaoService) {
        this.transacaoRepeticaoService = transacaoRepeticaoService;
    }

    @Before("execution(* com.projetointegrado.MeuBolso.conta.ContaService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findAll*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findSum*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.CategoriaDashboardService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.ContaDashboardService.find*(..)) || " +
            "execution(* com.projetointegrado.MeuBolso.dashboard.TransacoesDashboardsService.find*(..))")
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
            // Obtém ou cria um lock para o usuário
            locks.putIfAbsent(usuarioId, new ReentrantLock());
            ReentrantLock lock = locks.get(usuarioId);

            // Tenta obter o lock para o usuário antes de prosseguir
            if (lock.tryLock()) {
                try {
                    System.out.println("AOP -> Gerando transações fixas para usuário ID " + usuarioId + " e data " + data);
                    transacaoRepeticaoService.gerarTransacoes(data, usuarioId);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("AOP -> Uma geração de transação já está ocorrendo para o usuário ID " + usuarioId + ". Ignorando esta tentativa.");
            }
        } else {
            System.out.println("AOP -> Parâmetros não encontrados, pulando geração de transações.");
        }
    }
}

