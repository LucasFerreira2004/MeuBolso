package com.projetointegrado.MeuBolso.banco;

import com.projetointegrado.MeuBolso.banco.dto.BancoDTO;

import java.util.List;

public interface IBancoService {
    public List<BancoDTO> findAll();
    public BancoDTO findById(Long id);
}
