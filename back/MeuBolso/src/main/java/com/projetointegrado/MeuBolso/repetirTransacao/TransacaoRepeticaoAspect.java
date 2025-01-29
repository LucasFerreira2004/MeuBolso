package com.projetointegrado.MeuBolso.repetirTransacao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public class TransacaoRepeticaoAspect {
    private final TransacaoRepeticaoExecutor transacaoRepeticaoExecutor;

    public TransacaoRepeticaoAspect(TransacaoRepeticaoExecutor transacaoRepeticaoExecutor) {
        this.transacaoRepeticaoExecutor = transacaoRepeticaoExecutor;
    }

    @Before("execution(* com.projetointegrado.MeuBolso.conta.ContaService.find*(..)) || execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.findAll*(..))")
    public void gerarTransacoesFixasAntesDasBuscas(JoinPoint joinPoint) {
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
            System.out.println("AOP -> Gerando transações fixas para usuário ID " + usuarioId + " e data " + data);
            transacaoRepeticaoExecutor.executarGeracaoTransacoes(data, usuarioId); // ✅ Agora sempre executa em nova transação
        } else {
            System.out.println("AOP -> Parâmetros não encontrados, pulando geração de transações.");
        }
    }
}
