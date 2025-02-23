package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.meta.dto.MetaCardDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;

import java.util.List;

public interface IMetaService {
    public List<MetaDTO> findAll(String usuarioId);
    public MetaDTO findById(String usuarioId, Long metaId);
    public MetaDTO save(String usuarioId, MetaPostDTO meta);
    public MetaDTO update(String usuarioId, Long id, MetaPostDTO meta);
    public MetaDTO delete(String usuarioId, Long metaId);
    public List<MetaCardDTO> findAllCards(String usuarioId);
}
