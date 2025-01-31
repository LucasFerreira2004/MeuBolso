package com.projetointegrado.MeuBolso.dashboard;

import com.projetointegrado.MeuBolso.dashboard.dto.SaldoBalancoDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.TransacaoBalancoDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaExpandedDTO;
import com.projetointegrado.MeuBolso.dashboard.dto.CategoriaMinDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
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

    @GetMapping("/despesasCategoria")
    public List<CategoriaMinDTO> getDespesasCategorias(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.DESPESA);
    }

    @GetMapping("/receitasCategoria")
    public List<CategoriaMinDTO> getReceitasCategorias(@RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.RECEITA);
    }

    @GetMapping("/categoria/{id}")
    public CategoriaExpandedDTO getExpandedCategoria(@PathVariable Long id, @RequestParam int ano, @RequestParam int mes){
        LocalDate data = LocalDate.of(ano, mes, 1);
        data = data.with(TemporalAdjusters.lastDayOfMonth());

        String userId = usuarioService.getUsuarioLogadoId();
        return categoriaDashboardService.findExpandedCategoria(userId, id, data);
    }

    @GetMapping("transacoes/balanco")
    public List<TransacaoBalancoDTO> getBalancoTransacoes(@RequestParam int anoInicial, @RequestParam int mesInicial, @RequestParam int anoFinal, @RequestParam int mesFinal) {
        try {
            LocalDate dataInicial = LocalDate.of(anoInicial, mesInicial, 1);
            LocalDate dataFinal = LocalDate.of(anoFinal, mesFinal, 1);
            dataFinal = dataFinal.with(TemporalAdjusters.lastDayOfMonth());

            String userId = usuarioService.getUsuarioLogadoId();
            return transacoesDashboardsService.getTransacoesBalancos(userId, dataInicial, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("saldo/balanco")
    public List<SaldoBalancoDTO> getBalancoSaldos(@RequestParam int anoInicial, @RequestParam int mesInicial, @RequestParam int anoFinal, @RequestParam int mesFinal) {
        try {
            LocalDate dataInicial = LocalDate.of(anoInicial, mesInicial, 1);
            LocalDate dataFinal = LocalDate.of(anoFinal, mesFinal, 1);
            dataFinal = dataFinal.with(TemporalAdjusters.lastDayOfMonth());
            String userId = usuarioService.getUsuarioLogadoId();

            return contaDashboardService.getBalancoSaldos(userId, dataInicial, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
