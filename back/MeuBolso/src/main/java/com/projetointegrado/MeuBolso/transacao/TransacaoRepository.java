package com.projetointegrado.MeuBolso.transacao;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query(nativeQuery = true, value = """
        select * from transacao where usuario_id = :userId;
    """)
    public List<Transacao> findAllByUsuario(String userId);


    @Query(nativeQuery = true, value = """
        select * from transacao 
        where usuario_id = :userId and data between :dataInicio and :dataFim;
    """)
    public List<Transacao> findAllInRange(LocalDate dataInicio, LocalDate dataFim, String userId);

    @Query(nativeQuery = true, value = """
        select * from transacao where data <= :data and usuario_id = :userId;
    """)
    public List<Transacao> findAllBeforeDate(LocalDate data, String userId);
}
