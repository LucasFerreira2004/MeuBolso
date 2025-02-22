package com.projetointegrado.MeuBolso.orcamento.iterator;

import com.projetointegrado.MeuBolso.orcamento.Orcamento;

import java.util.List;
import java.util.NoSuchElementException;

public class OrcamentoIterator implements Iterator<Orcamento> {
    private final List<Orcamento> orcamentos;
    private int position = 0;

    public OrcamentoIterator(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    @Override
    public boolean hasNext() {
        return position < orcamentos.size();
    }

    @Override
    public Orcamento next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Não há mais orçamentos para iterar.");
        }
        return orcamentos.get(position++);
    }
}
