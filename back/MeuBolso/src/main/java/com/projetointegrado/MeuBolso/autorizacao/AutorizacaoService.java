package com.projetointegrado.MeuBolso.autorizacao;

import com.projetointegrado.MeuBolso.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    //faz a consulta dos usuários pro spring security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //executa toda vez que alguém tentar se autenticar
        return usuarioRepository.findByEmail(username);
    }
}
