package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.ArquivarCategoriaPatchDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import com.projetointegrado.MeuBolso.categoria.dto.CategoriaSaveDTO;
import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class categoriaController {

    @Autowired
    @Qualifier("categoriaService")
    private ICategoriaService categoriaService;

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @GetMapping
    public List<CategoriaDTO> findAllAtivas() {
        List<CategoriaDTO> result = categoriaService.findAllAtivas(usuarioService.getUsuarioLogadoId());
        return result;
    }

    @GetMapping("/{id}")
    public CategoriaDTO findCategoriaById(@PathVariable Long id) {
        CategoriaDTO result = categoriaService.findById(usuarioService.getUsuarioLogadoId(), id);
        return result;
    }

    @GetMapping("/receitas")
    public List<CategoriaDTO> findReceitas() {
        return categoriaService.findAllByReceita(usuarioService.getUsuarioLogadoId());
    }
    @GetMapping("/despesas")
    public List<CategoriaDTO> findDespesas() {
        return categoriaService.findAllByDespesa(usuarioService.getUsuarioLogadoId());
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.save(usuarioService.getUsuarioLogadoId(), categoriaSaveDTO);
    }
    @PutMapping("{id}")
    public CategoriaDTO update(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoriaSaveDTO) {
        return categoriaService.update(usuarioService.getUsuarioLogadoId(), id, categoriaSaveDTO);
    }

    //RotasRerentes específicas das arquivadas
    @GetMapping("/arquivadas")
    public List<CategoriaDTO> findAllArquivadas() {
        List<CategoriaDTO> result = categoriaService.findAllArquivadas(usuarioService.getUsuarioLogadoId());
        return result;
    }

    @PatchMapping("/arquivadas/{id}")
    public CategoriaDTO atualizarStatusAtiva(@PathVariable Long id, @RequestBody ArquivarCategoriaPatchDTO dto, BindingResult bindingResult) throws ValoresNaoPermitidosException {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        return categoriaService.atualizarStatusAtiva(usuarioService.getUsuarioLogadoId(), id, dto.ativa());
    }
}
