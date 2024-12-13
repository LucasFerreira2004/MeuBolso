package com.projetointegrado.MeuBolso.tipoConta;

import org.springframework.stereotype.Service;

@Service
public class TipoContaService {
    private TipoContaRepository tipoContaRepository;

    public TipoConta findTipoContaById(Long id) {
        return tipoContaRepository.findById(id).orElse(null);
    }
}
