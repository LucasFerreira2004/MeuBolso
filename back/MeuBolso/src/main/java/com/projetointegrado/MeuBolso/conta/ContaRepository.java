package com.projetointegrado.MeuBolso.conta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public  interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query( nativeQuery = true, value = """
        select * from conta where usuario_id = :id_usuario
    """)
    List<Conta> findAllByUsuarioId(Long id_usuario);
}
