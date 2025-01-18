package com.projetointegrado.MeuBolso.meta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    @Query(nativeQuery = true, value = """
        select * from meta where usuario_id = :idUsuario
        """)
        public List<Meta> findAllByUsuario(String idUsuario);
}
