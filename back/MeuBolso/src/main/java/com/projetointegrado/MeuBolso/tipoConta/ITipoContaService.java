package com.projetointegrado.MeuBolso.tipoConta;

import com.projetointegrado.MeuBolso.tipoConta.dto.TipoContaDTO;

import java.util.List;

public interface ITipoContaService {
    public TipoContaDTO findById(Long id);
    public List<TipoContaDTO> findAll();
}
