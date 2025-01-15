package com.projetointegrado.MeuBolso.meta;

import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService implements IMetaService {
    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional(readOnly = true)
    public List<Meta> findAll(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        return metaRepository.findAllByUsuario(usuarioId);              // Retornar DTO
    }

    @Transactional(readOnly = true)
    public Optional<Meta> findById(String usuarioId, Long metaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        return metaRepository.findById(metaId);              // Fazer tratamento de exceções
    }

    @Transactional(readOnly = true)
    public Meta save(Meta meta) {
        return metaRepository.save(meta);
    }

    @Transactional(readOnly = true)
    public Meta update(Meta meta) {
        return metaRepository.save(meta);
    }

    @Transactional(readOnly = true)
    public void delete(String usuarioId, String metaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
    }
}
