package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarCategoriasIniciaisService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioValidateService usuarioValidateService;
    public void criarCategorias(String userId) {
        Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario não encontrado"));

    }

    private Categoria criarCategoria(Usuario usuario, String nome, TipoCategoria tipo) {
        Categoria categoria = new Categoria(null, nome, tipo, "000", true, usuario, true);
        return categoria;
    }
}
