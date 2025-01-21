package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidateService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validate(String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> entidadeNaoEncontrada);
    }

    public Usuario validateAndGet(String userId, EntidadeNaoEncontradaException entidadeNaoEncontrada) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> entidadeNaoEncontrada);
        return usuario;
    }
}
