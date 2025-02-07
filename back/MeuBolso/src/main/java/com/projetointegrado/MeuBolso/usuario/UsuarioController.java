package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        usuarioService.save(usuarioDTO);
        ResponseEntity.ok("Usuário cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

}
