package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;

import java.util.List;

public interface ICategoriaService {
    public List<CategoriaDTO> findAll(String usuarioId);
    public CategoriaDTO findById(String usuarioId, Long id);
    public List<CategoriaDTO> findAllByReceita(String usuarioId);
    public List<CategoriaDTO> findAllByDespesa(String usuarioId);
    public CategoriaDTO save(String usuarioId, CategoriaSaveDTO dto);
    public CategoriaDTO update(String usuarioId, Long id, CategoriaSaveDTO dto);
    public CategoriaDTO arquivar(String usuarioId, Long id);

}
