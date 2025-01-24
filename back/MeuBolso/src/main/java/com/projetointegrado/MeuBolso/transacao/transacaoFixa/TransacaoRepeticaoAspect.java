package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public class TransacaoRepeticaoAspect {
    private final TransacaoRepeticaoService transacaoRepeticaoService;

    public TransacaoRepeticaoAspect(TransacaoRepeticaoService transacaoRepeticaoService) {
        this.transacaoRepeticaoService = transacaoRepeticaoService;
    }

    @Before("execution(* com.projetointegrado.MeuBolso.conta.ContaService.find*(..)) || execution(* com.projetointegrado.MeuBolso.transacao.TransacaoService.find*(..))")
    public void gerarTransacoesFixasAntesDasBuscas(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length < 2 || !(args[0] instanceof LocalDate) || !(args[1] instanceof String)) {
            System.out.println("AOP -> Parâmetros inválidos, não gerando transações.");
            return;
        }

        LocalDate data = (LocalDate) args[0];
        String  usuarioId = (String) args[1];
        System.out.println("AOP -> Gerando transações fixas antes de buscar contas ou transações.");
        transacaoRepeticaoService.gerarTransacoes();
    }
}
