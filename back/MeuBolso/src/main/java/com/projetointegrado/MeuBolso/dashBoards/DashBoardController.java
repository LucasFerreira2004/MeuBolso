package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.dashBoards.dto.CategoriaMinDTO;
import com.projetointegrado.MeuBolso.transacao.TipoTransacao;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value ="/dashboards")
public class DashBoardController {
    @Autowired
    private CategoriaDashboardService categoriaDashboardService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/despesasCategoria")
    public List<CategoriaMinDTO> getDespesasCategorias(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            String userId = usuarioService.getUsuarioLogadoId();
            return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.DESPESA);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/receitasCategoria")
    public List<CategoriaMinDTO> getReceitasCategorias(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            String userId = usuarioService.getUsuarioLogadoId();
            return categoriaDashboardService.findAllValorTotalCategoria(userId, data, TipoTransacao.RECEITA);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
