package com.projetointegrado.MeuBolso.orcamento.iterator;

import com.projetointegrado.MeuBolso.orcamento.Orcamento;

import java.util.List;

public class OrcamentoAgregado {
    private final List<Orcamento> orcamentos;

    public OrcamentoAgregado(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    public Iterator<Orcamento> iterator() {
        return new OrcamentoIterator(orcamentos);
    }
}
