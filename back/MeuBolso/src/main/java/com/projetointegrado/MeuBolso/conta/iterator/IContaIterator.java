package com.projetointegrado.MeuBolso.conta.iterator;

import com.projetointegrado.MeuBolso.conta.Conta;

import java.time.LocalDate;

public interface IContaIterator {
    public Conta getNext(LocalDate data);
    public boolean hasNext();
}
