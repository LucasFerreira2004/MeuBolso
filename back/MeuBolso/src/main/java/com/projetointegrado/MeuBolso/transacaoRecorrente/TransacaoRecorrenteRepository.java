package com.projetointegrado.MeuBolso.transacaoRecorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long> {
    @Query(nativeQuery = true, value = """
        select * from TRANSACAO_RECORRENTE where usuario_id = :userId;
    """)
    public List<TransacaoRecorrente> findAllByUsuario(String userId);
}