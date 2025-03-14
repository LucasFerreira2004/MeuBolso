package com.projetointegrado.MeuBolso.conta;

import com.projetointegrado.MeuBolso.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public  interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query(nativeQuery = true, value = """
        select * from conta where usuario_id = :idUsuario;
    """)
    public List<Conta> findAllByUsuario(String idUsuario);

    @Query(nativeQuery = true, value = """
        select * from conta where usuario_id = :userId and descricao = :descricao;
    """)
    public Conta findByDescricao(String userId, String descricao);
}
