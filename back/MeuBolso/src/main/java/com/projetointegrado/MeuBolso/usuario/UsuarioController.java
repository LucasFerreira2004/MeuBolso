package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioSaveDTO> criarUsuario(@RequestBody @Valid UsuarioSaveDTO usuarioSaveDTO) {
        usuarioService.save(usuarioSaveDTO);
        ResponseEntity.ok("Usuário cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSaveDTO);
    }

    @GetMapping
    public UsuarioDTO findUsuario(){
        String userId = usuarioService.getUsuarioLogadoId();
        return usuarioService.findById(userId);
    }
}
