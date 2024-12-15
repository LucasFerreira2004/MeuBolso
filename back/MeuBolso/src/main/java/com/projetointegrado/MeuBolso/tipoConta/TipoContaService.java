package com.projetointegrado.MeuBolso.tipoConta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoContaService {
    @Autowired
    private TipoContaRepository tipoContaRepository;

    @Transactional(readOnly = true)
    public TipoContaDTO findTipoContaById(Long id) {
        //tratar null
        TipoConta tipo = tipoContaRepository.findById(id).orElse(null);
        return new TipoContaDTO(tipo);
    }

    @Transactional(readOnly = true)
    public List<TipoContaDTO> findAll() {
        List<TipoConta> resultado = tipoContaRepository.findAll();
        return resultado.stream().map(TipoContaDTO::new).toList();
    }
}
