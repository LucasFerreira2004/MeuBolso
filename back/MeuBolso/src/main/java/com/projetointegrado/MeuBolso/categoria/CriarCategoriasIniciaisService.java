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
            criarCategoriaInterna(usuario, "DepositoInicial*", TipoCategoria.RECEITA, "000"),
            criarCategoriaInterna(usuario, "ReajusteSaldoAumento*", TipoCategoria.RECEITA, "000"),
            criarCategoriaInterna(usuario, "ReajusteSaldoDecremento*", TipoCategoria.DESPESA, "000"),

            criarCategoriaExterna(usuario, "salário", TipoCategoria.RECEITA, "33A841"),
            criarCategoriaExterna(usuario, "investimentos", TipoCategoria.RECEITA, "0775BA"),
            criarCategoriaExterna(usuario, "presente", TipoCategoria.RECEITA, "8755BC"),
            criarCategoriaExterna(usuario, "renda extra", TipoCategoria.RECEITA, "CE601B"),
            criarCategoriaExterna(usuario, "outros", TipoCategoria.RECEITA, "BC7D18"),

            criarCategoriaExterna(usuario, "casa", TipoCategoria.DESPESA, "8755BC"),
            criarCategoriaExterna(usuario, "alimentação", TipoCategoria.DESPESA, "BA0707"),
            criarCategoriaExterna(usuario, "transporte", TipoCategoria.DESPESA,"C19F13"),
            criarCategoriaExterna(usuario, "educação", TipoCategoria.DESPESA, "33A841"),
            criarCategoriaExterna(usuario, "lazer", TipoCategoria.DESPESA, "57BEA1"),
            criarCategoriaExterna(usuario, "roupas", TipoCategoria.DESPESA, "5797BE"),
            criarCategoriaExterna(usuario, "saúde", TipoCategoria.DESPESA, "C92D78"),
            criarCategoriaExterna(usuario, "pagamentos", TipoCategoria.DESPESA, "818181n")
        );
        categoriaRepository.saveAll(categoriasPadrao);
    }

    private Categoria criarCategoriaInterna(Usuario usuario, String nome, TipoCategoria tipo, String cor) {
        Categoria categoria = new Categoria(null, nome, tipo, cor, true, usuario, true);
        return categoria;
    }
    private Categoria criarCategoriaExterna(Usuario usuario, String nome, TipoCategoria tipo, String cor) {
        Categoria categoria = new Categoria(null, nome, tipo, cor, true, usuario, false);
        return categoria;
    }
}
