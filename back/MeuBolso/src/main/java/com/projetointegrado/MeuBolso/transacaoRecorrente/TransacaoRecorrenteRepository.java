package com.projetointegrado.MeuBolso.transacaoRecorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long> {
    @Query(nativeQuery = true, value = """
        select * from TRANSACAO_RECORRENTE 
        where usuario_id = :userId 
        and ativa = true 
        order by (data_cadastro) desc;
    """)
    public List<TransacaoRecorrente> findAllByUsuario(String userId);

    @Query(nativeQuery = true, value = """
        select * from TRANSACAO_RECORRENTE 
        where usuario_id = :userId 
        and ativa = false
        order by (data_cadastro) desc;
    """)
    public List<TransacaoRecorrente> findAllArquivadasByUsuario(String userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        delete from TRANSACAO
        where transacao_recorrente_id = :id
        and data >= :data
    """)
    public void deleteAllAfterDate(Long id, LocalDate data);
}