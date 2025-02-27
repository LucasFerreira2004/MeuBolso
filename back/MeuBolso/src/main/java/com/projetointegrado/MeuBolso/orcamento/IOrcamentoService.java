package com.projetointegrado.MeuBolso.orcamento;

import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoDTO;
import com.projetointegrado.MeuBolso.orcamento.dto.OrcamentoPostDTO;

import java.time.LocalDate;
import java.util.List;

public interface IOrcamentoService {
    public List<OrcamentoDTO> findAll(String usuarioId);
    public OrcamentoDTO findById(Long id, String usuarioId);
    public OrcamentoDTO save(OrcamentoPostDTO orcamento, String UsuarioId);
    public OrcamentoDTO update(Long id, OrcamentoPostDTO orcamento, String usuarioId);
    public OrcamentoDTO deleteById(String usuarioId, Long id);
    public List<OrcamentoDTO> findOrcamentosByPeriodo(String usuarioId, Integer mes, Integer ano);
}
