package com.projetointegrado.MeuBolso.orcamento;

import java.util.List;

public interface IOrcamentoService {
    public List<Orcamento> findAll();
    public Orcamento findById(Long id);
    public Orcamento save(Orcamento Orcamento);
    public void deleteById(Long id);
    public Orcamento update(Long id, Orcamento Orcamento);
}
