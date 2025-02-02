package com.projetointegrado.MeuBolso.dashboard;

import com.projetointegrado.MeuBolso.categoria.*;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaDadosDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaExpandedDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.transacao.Transacao;
import com.projetointegrado.MeuBolso.transacao.TransacaoRepository;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import com.projetointegrado.MeuBolso.transacao.dto.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaDadosService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional
    public List<CategoriaDadosDTO> getDadosCategorias(String userId, LocalDate dataFinal, TipoTransacao tipo) {
        LocalDate dataInicial = dataFinal.with(TemporalAdjusters.firstDayOfMonth());
        TipoCategoria tipoCategoria = TipoCategoria.valueOf(tipo.name());
        List<Categoria> categorias = categoriaRepository.findAllNotInternByTipo(userId, tipoCategoria.name());
        List<CategoriaDadosDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias) {
            CategoriaDadosDTO dto = getCategoriaDadosDTO(userId, dataInicial, dataFinal, tipo, categoria);
            if(dto != null) dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public CategoriaExpandedDTO findExpandedCategoria(String userId, Categoria categoria, LocalDate dataFinal) {
        LocalDate dataInicial = dataFinal.with(TemporalAdjusters.firstDayOfMonth());
        TipoTransacao tipoTransacao = TipoTransacao.valueOf(categoria.getTipo().name());
        CategoriaDadosDTO dadosDTO = getCategoriaDadosDTO(userId, dataInicial, dataFinal, tipoTransacao, categoria);
        if (dadosDTO == null) return null;

        List<Transacao> transacoes = transacaoRepository.findAllInRangeByCategoria(userId, dataInicial, dataFinal, categoria.getId());
        List<TransacaoDTO> transacoesDTO = transacoes.stream().map(t -> new TransacaoDTO(t)).toList();
        return new CategoriaExpandedDTO(categoria.getId(), categoria.getCor(), categoria.getNome(),
                dadosDTO.valor(), dadosDTO.percentual(), transacoesDTO);
    }

    private CategoriaDadosDTO getCategoriaDadosDTO(String userId, LocalDate dataInicial, LocalDate dataFinal, TipoTransacao tipo, Categoria categoria) {
        BigDecimal totalValorMensal = transacaoRepository.getSumInRangeByTipo(dataInicial, dataFinal, userId, tipo.name());
        System.out.println("getDadosCategoria -> totalValorMensal:" + totalValorMensal);
        BigDecimal valorCategoria =  transacaoRepository.getSumInRangeByCategoria(dataInicial, dataFinal, categoria.getId(), userId); //mudar para ficar no service de transacao
        System.out.println("getDadosCategoria -> valorCategoria:" + valorCategoria);
        if (valorCategoria == null || totalValorMensal == null || totalValorMensal.compareTo(BigDecimal.ZERO) == 0) return null;

        BigDecimal prctGasto = valorCategoria
                .multiply(new BigDecimal(100))
                .divide(totalValorMensal, 2, RoundingMode.HALF_DOWN);
        return new CategoriaDadosDTO(categoria, valorCategoria, prctGasto);
    }
}
