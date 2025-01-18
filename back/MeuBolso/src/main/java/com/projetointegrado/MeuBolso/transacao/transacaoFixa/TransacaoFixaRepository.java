package com.projetointegrado.MeuBolso.transacao.transacaoFixa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoFixaRepository extends JpaRepository<TransacaoFixa, Long> {
    @Query(nativeQuery = true, value = """
        select * from transacao_fixa where usuario_id = :userId;
    """)
    public List<TransacaoFixa> findAllByUsuario(String userId);
}