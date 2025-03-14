package com.projetointegrado.MeuBolso.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    UserDetails findByEmail(String email);
    @Query(nativeQuery = true, value = """
        select * from usuario where id = :id
    """)
    Usuario findByIdUserDetails(String id);

}
