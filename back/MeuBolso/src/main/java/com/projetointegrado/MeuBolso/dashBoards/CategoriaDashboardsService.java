package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.categoria.Categoria;
import com.projetointegrado.MeuBolso.dashBoards.dto.CategoriaBuscaInternaDTO;
import com.projetointegrado.MeuBolso.dashBoards.dto.ValorTotalCategoriaDTO;
import com.projetointegrado.MeuBolso.globalExceptions.EntidadeNaoEncontradaException;
import com.projetointegrado.MeuBolso.usuario.UsuarioValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaDashboardsService {
    @Autowired
    private CategoriaBuscasInternasService categoriaBuscasInternasService;

    @Autowired
    private UsuarioValidateService usuarioValidateService;

    @Transactional
    public List<ValorTotalCategoriaDTO> findAllValorTotalCategoria(String userId, LocalDate dataFinal) {
        usuarioValidateService.validate(userId, new EntidadeNaoEncontradaException("{token}", "usuario nao encontrado"));

        List<CategoriaBuscaInternaDTO> buscaInternaDTOs = categoriaBuscasInternasService.buscaCategoriasInternas(userId, dataFinal);
        List<ValorTotalCategoriaDTO> valorTotalDTOs = new ArrayList<>();
        for (CategoriaBuscaInternaDTO buscaInternaDTO: buscaInternaDTOs) {
            Categoria categoria = buscaInternaDTO.categoria();
            valorTotalDTOs.add(new ValorTotalCategoriaDTO(categoria.getId(), categoria.getCor(), categoria.getNome(),
                    buscaInternaDTO.valor(), buscaInternaDTO.percentual()));
        }
        return valorTotalDTOs;
    }
}
