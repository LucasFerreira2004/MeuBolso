package com.projetointegrado.MeuBolso.meta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    @Query(nativeQuery = true, value = "select * from meta where usuario_id = :idUsuario")
    public List<Meta> findAllByUsuario(String idUsuario);

//    @Query
    Optional<Meta> findByDescricaoAndUsuarioId(String descricao, String usuarioId);
}
