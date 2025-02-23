package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;

import java.util.List;

public interface ICategoriaService {
    public List<CategoriaDTO> findAllAtivas(String usuarioId);
    public List<CategoriaDTO> findAllArquivadas(String usuarioId);
    public CategoriaDTO findById(String usuarioId, Long id);
    public List<CategoriaDTO> findAllByReceita(String usuarioId);
    public List<CategoriaDTO> findAllByDespesa(String usuarioId);
    public CategoriaDTO save(String usuarioId, CategoriaSaveDTO dto);
    public CategoriaDTO update(String usuarioId, Long id, CategoriaSaveDTO dto);
    public CategoriaDTO atualizarStatusAtiva(String usuarioId, Long id, Boolean ativa);

}
