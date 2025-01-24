package com.projetointegrado.MeuBolso.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query(nativeQuery = true, value = """
        select * from transacao where usuario_id = :userId;
    """)
    public List<Transacao> findAllByUsuario(String userId);


    @Query(nativeQuery = true, value = """
        select * from transacao where data = '____-:month-:year';
    """)
    public List<Transacao> findAllByMonthYear(int month, int year);
}
