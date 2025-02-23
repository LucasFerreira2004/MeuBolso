package com.projetointegrado.MeuBolso.dashboard;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.CategoriaValidateService;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaDadosDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaExpandedDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaMinDTO;
import com.projetointegrado.MeuBolso.globalExceptions.AcessoNegadoException;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaDashboardService {
    @Autowired
    private CategoriaDadosService categoriaDadosService;

    @Autowired
    private CategoriaValidateService categoriaValidateService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Transactional
    public List<CategoriaMinDTO> findAllValorTotalCategoria(String userId, LocalDate dataFinal, TipoTransacao tipo) {
        usuarioValidateService.validate(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado"));

        List<CategoriaDadosDTO> buscaInternaDTOs = categoriaDadosService.getDadosCategorias(userId, dataFinal, tipo);
        List<CategoriaMinDTO> valorTotalDTOs = new ArrayList<>();

        for (CategoriaDadosDTO buscaInternaDTO: buscaInternaDTOs) {
            Categoria categoria = buscaInternaDTO.categoria();
            valorTotalDTOs.add(new CategoriaMinDTO(categoria.getId(), categoria.getCor(), categoria.getNome(),
                    buscaInternaDTO.valor(), buscaInternaDTO.percentual()));
        }
        return valorTotalDTOs;
    }

    @Transactional
    public CategoriaExpandedDTO findExpandedCategoria(String userId, Long id, LocalDate dataFinal) {
        Categoria categoria = categoriaValidateService.validateAndGet(id, userId,
                new EntidadeNaoEncontradaException("{id}", "categoria nao encontrada"), new AcessoNegadoException());
        return categoriaDadosService.findExpandedCategoria(userId, categoria, dataFinal);
    }
}
