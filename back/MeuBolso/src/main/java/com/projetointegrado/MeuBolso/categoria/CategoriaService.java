package com.projetointegrado.MeuBolso.categoria;

import com.projetointegrado.MeuBolso.categoria.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> findCategoria() {
        List<Categoria> result = categoriaRepository.findAll();
        return result.stream().map(CategoriaDTO::new).toList();
    }

    public List<CategoriaDTO> getAllReceita() {
        return null;
    }

    public List<CategoriaDTO> getAllDespesa() {
        return null;
    }
}
