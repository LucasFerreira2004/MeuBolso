package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.conta.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface IContaService {
    public ContaDTO findById(String idUsuario, Long id, LocalDate data);
    public List<ContaDTO> findAll(String idUsuario, LocalDate data);
    public List<ContaMinDTO> findAllMin(String idUsuario, LocalDate data);
    public SaldoTotalDTO findoSaldo(String idUsuario, LocalDate data);

    public ContaDTO save(String userID, ContaPostDTO dto);
    public ContaDTO update(Long id, ContaPutDTO dto, String userId);
    public ContaDTO delete(Long id, String idUsuario);
}
