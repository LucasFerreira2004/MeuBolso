package com.projetointegrado.MeuBolso.banco;

import com.projetointegrado.MeuBolso.banco.dto.BancoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BancoService {
    @Autowired
    BancoRepository bancoRepository;

    @Transactional(readOnly = true)
    public List<BancoDTO> findAll(){
        return bancoRepository.findAll().stream().map(BancoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BancoDTO findById(Long id) {
        return bancoRepository.findById(id).map(BancoDTO::new).orElse(null);
    }

}
