package com.projetointegrado.MeuBolso.orcamento.iterator;

import com.projetointegrado.MeuBolso.orcamento.Orcamento;

public interface Iterator<T> {
    public boolean hasNext();
    public T next();
}
