package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioLoginDTO;
import com.projetointegrado.MeuBolso.usuario.exception.SenhaIncorretaException;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Registra um novo usuario")
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
        UsuarioDTO userSalvo = new UsuarioDTO(usuarioService.salvarUsuario(usuario));
        ResponseEntity.ok("Usuário cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }

    @Operation(summary = "Realiza login de um usuario")
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        UsuarioDTO usuarioDTO = usuarioService.login(usuarioLoginDTO.getEmail(), usuarioLoginDTO.getSenha());
        return ResponseEntity.ok(usuarioDTO);
    }

    @Operation(summary = "Retorna todos os usuarios registrados")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> list = usuarioService.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Retorna um usuario a partir de um id indicado")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        Optional<UsuarioDTO> usuario = Optional.ofNullable(usuarioService.findById(id));
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
