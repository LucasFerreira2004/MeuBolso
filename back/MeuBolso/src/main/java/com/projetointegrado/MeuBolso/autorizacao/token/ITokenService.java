package com.projetointegrado.MeuBolso.autorizacao.token;

import com.projetointegrado.MeuBolso.usuario.Usuario;

public interface ITokenService {
    public String gerarToken(Usuario usuario);
    public String validarToken(String token);
}
