package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;

import java.util.List;

public interface IOrcamentoService {
    public List<OrcamentoDTO> findAll(String usuarioId);
    public OrcamentoDTO findById(Long id, String usuarioId);
    public OrcamentoDTO save(String UserId, OrcamentoPostDTO orcamento);
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamento, String usuarioId);
    public void deleteById(String usuarioId, Long id);
}
