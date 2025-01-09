package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;

import java.util.List;

public interface IContaService {
    public ContaDTO findById(String idUsuario, Long id);
    public List<ContaDTO> findAll(String idUsuario);
    public List<ContaMinDTO> findAllMin(String idUsuario);
    public ContaDTO save(String userID, ContaPostDTO dto);
    public ContaDTO update(Long id, ContaPutDTO dto, String userId);
    public ContaDTO delete(Long id, String idUsuario);
    public SaldoTotalDTO getSaldo(String idUsuario);
}
