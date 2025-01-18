package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.meta.exception.MetaNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetaService implements IMetaService {
    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<MetaDTO> findAll(String usuarioId) {
        List<Meta> result = metaRepository.findAllByUsuario(usuarioId);

        return result.stream().map(MetaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MetaDTO findById(String usuarioId, Long metaId) {
        Meta result = metaRepository.findById(metaId).orElse(null);

        if(result == null)                                  throw new MetaNaoEncontradaException();
        if(!result.getUsuario().getId().equals(usuarioId))  throw new AcessoNegadoException();

        return new MetaDTO(result);
    }

    @Transactional
    public MetaDTO save(String usuarioId, MetaPostDTO meta) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if(usuario == null) throw new UsuarioNaoEncontradoException();

        Meta metaEntity = new Meta(meta);
        metaEntity.setUsuario(usuario);

        return new MetaDTO(metaRepository.save(metaEntity));
    }

    @Transactional
    public MetaDTO update(String usuarioId, Long id, MetaPostDTO meta) {
        Meta metaEntity = metaRepository.findById(id).orElse(null);
        if(metaEntity == null)                                  throw new MetaNaoEncontradaException();
        if(!metaEntity.getUsuario().getId().equals(usuarioId))  throw new AcessoNegadoException();

        metaEntity.setDescricao(meta.getDescricao());
        metaEntity.setUrlImg(meta.getUrlImg());
        metaEntity.setValorMeta(meta.getValorMeta());

        return new MetaDTO(metaRepository.save(metaEntity));
    }

    @Transactional
    public void delete(String usuarioId, Long id) {
        Meta metaEntity = metaRepository.findById(id).orElse(null);

        if(metaEntity == null)                                  throw new MetaNaoEncontradaException();
        if(!metaEntity.getUsuario().getId().equals(usuarioId))  throw new AcessoNegadoException();

        metaRepository.delete(metaEntity);
    }
}
