package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.meta.dto.MetaDTO;
import com.projetointegrado.MeuBolso.meta.dto.MetaPostDTO;
import com.projetointegrado.MeuBolso.meta.exception.DescricaoUnicaException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
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

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Autowired
    private MetaValidateService metaValidateService;

    @Transactional(readOnly = true)
    public List<MetaDTO> findAll(String usuarioId) {
        List<Meta> meta = metaRepository.findAllByUsuario(usuarioId);
        return meta.stream().map(MetaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MetaDTO findById(String usuarioId, Long metaId) {
        Meta meta = metaRepository.findById(metaId).orElse(null);
        metaValidateService.validate(metaId, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "meta nao encontrado a partir do id"),
                new AcessoNegadoException());
        assert meta != null;
        return new MetaDTO(meta);
    }

    @Transactional
    public MetaDTO save(String usuarioId, MetaPostDTO metaDTO) {
        Meta meta = saveAndValidate(usuarioId, null, metaDTO);
        return new MetaDTO(meta);
    }

    @Transactional
    public MetaDTO update(String usuarioId, Long id, MetaPostDTO metaDTO) {
        metaValidateService.validate(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "meta nao encontrado a partir do id"),
                new AcessoNegadoException());
        Meta meta = saveAndValidate(usuarioId, id, metaDTO);
        return new MetaDTO(meta);
    }

    @Transactional
    public MetaDTO delete(String usuarioId, Long id) {
        Meta meta = metaValidateService.validateAndGet(id, usuarioId,
                new EntidadeNaoEncontradaException("{id}", "meta nao encontrado a partir do id"),
                new AcessoNegadoException());
        metaRepository.delete(meta);
        return new MetaDTO(meta);
    }


    private Meta saveAndValidate(String usuarioId, Long id, MetaPostDTO metaDTO) {
        Usuario usuario = usuarioValidateService.validateAndGet(usuarioId,
                new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado a partir do token"));

        metaValidateService.validateDescricaoUnica(metaDTO.getDescricao(), usuarioId, id, new DescricaoUnicaException());

        Meta meta;
        if (id != null) {
            // Busca a meta existente para atualizar
            meta = metaRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("{id}", "Meta não encontrada para atualização"));

            // Atualiza os campos da meta existente
            meta.setValorMeta(metaDTO.getValorMeta());
            meta.setDescricao(metaDTO.getDescricao());
            meta.setUrlImg(metaDTO.getUrlImg());
        } else {
            // Cria nova meta se for um save
            meta = new Meta(null, metaDTO.getValorMeta(), metaDTO.getDescricao(), metaDTO.getUrlImg(), usuario);
        }

        System.out.println(meta);
        return metaRepository.save(meta);
    }
}
