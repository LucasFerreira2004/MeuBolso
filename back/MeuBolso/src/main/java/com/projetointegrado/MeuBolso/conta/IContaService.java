package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;

import java.util.Date;
import java.util.List;

public interface IContaService {
    public ContaDTO findById(String idUsuario, Long id, Date data);
    public List<ContaDTO> findAll(String idUsuario, Date data);
    public List<ContaMinDTO> findAllMin(String idUsuario, Date data);
    public SaldoTotalDTO getSaldo(String idUsuario, Date data);

    public ContaDTO save(String userID, ContaPostDTO dto);
    public ContaDTO update(Long id, ContaPutDTO dto, String userId);
    public ContaDTO delete(Long id, String idUsuario);
}
