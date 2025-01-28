package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CriarCategoriasIniciaisService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioValidateService usuarioValidateService;
    public void criarCategorias(String userId) {
        Usuario usuario = usuarioValidateService.validateAndGet(userId, new EntidadeNaoEncontradaException("{token}", "usuario não encontrado"));
        List<Categoria> categoriasPadrao = List.of(
            criarCategoriaInterna(usuario, "DepositoInicial*", TipoCategoria.RECEITA),
            criarCategoriaInterna(usuario, "ReajusteSaldoAumento*", TipoCategoria.RECEITA),
            criarCategoriaInterna(usuario, "ReajusteSaldoDecremento*", TipoCategoria.DESPESA),

            criarCategoriaExterna(usuario, "salario", TipoCategoria.RECEITA),
            criarCategoriaExterna(usuario, "investimentos", TipoCategoria.RECEITA),
            criarCategoriaExterna(usuario, "presente", TipoCategoria.RECEITA),
            criarCategoriaExterna(usuario, "renda extra", TipoCategoria.RECEITA),
            criarCategoriaExterna(usuario, "outros", TipoCategoria.RECEITA),

            criarCategoriaExterna(usuario, "casa", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "alimentacão", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "transporte", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "educacão", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "lazer", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "roupas", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "saúde", TipoCategoria.DESPESA),
            criarCategoriaExterna(usuario, "pagamentos", TipoCategoria.DESPESA)
        );
        categoriaRepository.saveAll(categoriasPadrao);
    }

    private Categoria criarCategoriaInterna(Usuario usuario, String nome, TipoCategoria tipo) {
        Categoria categoria = new Categoria(null, nome, tipo, "000", true, usuario, true);
        return categoria;
    }
    private Categoria criarCategoriaExterna(Usuario usuario, String nome, TipoCategoria tipo) {
        Categoria categoria = new Categoria(null, nome, tipo, "000", true, usuario, false);
        return categoria;
    }
}
