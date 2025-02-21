package com.projetointegrado.MeuBolso.dashboard;

import com.projetointegrado.MeuBolso.dashboard.dto.SaldoBalancoDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.TransacaoBalancoDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaExpandedDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaMinDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping(value ="/dashboards")
public class DashboardController {
    @Autowired
    private CategoriaDashboardService categoriaDashboardService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TransacoesDashboardsService transacoesDashboardsService;

    @Autowired
    private ContaDashboardService contaDashboardService;

    @Operation(summary = "Dashboard de valores total por categoria de despesas no mes")
    @GetMapping("/despesasCategoria")
    public List<CategoriaMinDTO> getDespesasCategorias(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.DESPESA);
    }

    @Operation(summary = "Dashboard de valores total por categoria de receitas no mes")
    @GetMapping("/receitasCategoria")
    public List<CategoriaMinDTO> getReceitasCategorias(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.RECEITA);
    }

    @Operation(summary = "Informacoes de transacoes feitas por categoria no mes")
    @GetMapping("/categoria/{id}")
    public CategoriaExpandedDTO getExpandedCategoria(@PathVariable Long id, @RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findExpandedCategoria(userId, id, data);
    }

    @Operation(summary = "Dashboard do total de transacoes separado por despesa e receita")
    @GetMapping("transacoes/balanco")
    public List<TransacaoBalancoDTO> getBalancoTransacoes(@RequestParam int anoInicial, @RequestParam int mesInicial, @RequestParam int anoFinal, @RequestParam int mesFinal) {
        try {
            LocalDate dataInicial = LocalDate.of(anoInicial, mesInicial, 1);
            LocalDate dataFinal = LocalDate.of(anoFinal, mesFinal, 1);
            dataFinal = dataFinal.with(TemporalAdjusters.lastDayOfMonth());

            String userId = usuarioService.getUsuarioLogadoId();
            return transacoesDashboardsService.findTransacoesBalanco(userId, dataInicial, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Dashboard de saldo do usuario por mes")
    @GetMapping("saldo/balanco")
    public List<SaldoBalancoDTO> getBalancoSaldos(@RequestParam int anoInicial, @RequestParam int mesInicial, @RequestParam int anoFinal, @RequestParam int mesFinal) {
        try {
            LocalDate dataInicial = LocalDate.of(anoInicial, mesInicial, 1);
            LocalDate dataFinal = LocalDate.of(anoFinal, mesFinal, 1);
            dataFinal = dataFinal.with(TemporalAdjusters.lastDayOfMonth());
            String userId = usuarioService.getUsuarioLogadoId();

            return contaDashboardService.findSaldosBalanco(userId, dataInicial, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
