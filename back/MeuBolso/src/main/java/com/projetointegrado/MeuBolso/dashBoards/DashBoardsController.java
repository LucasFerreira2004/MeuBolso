package com.projetointegrado.MeuBolso.dashBoards;

import com.projetointegrado.MeuBolso.dashBoards.dto.ValorTotalCategoriaDTO;
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
public class DashBoardsController {
    @Autowired
    private CategoriaDashboardsService categoriaDashboardsService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/despesasTotaisCategorias")
    public List<ValorTotalCategoriaDTO> getDespesasCategorias(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            String userId = usuarioService.getUsuarioLogadoId();
            return categoriaDashboardsService.findAllValorTotalCategoria(userId, data);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
