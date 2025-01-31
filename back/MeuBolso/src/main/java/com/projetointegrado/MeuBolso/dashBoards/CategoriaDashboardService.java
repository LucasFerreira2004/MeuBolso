package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.categoria.TipoCategoria;
import com.projetointegrado.MeuBolso.dashBoards.dto.CategoriaDadosDTO;
import com.projetointegrado.MeuBolso.dashBoards.dto.CategoriaMinDTO;
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
}
