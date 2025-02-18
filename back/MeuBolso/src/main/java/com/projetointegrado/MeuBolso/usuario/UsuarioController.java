package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @Operation(summary = "Retorna o usuário logado")
    @GetMapping
    public UsuarioDTO findUsuario(){
        String userId = usuarioService.getUsuarioLogadoId();
        return usuarioService.findById(userId);
    }
    
    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioSaveDTO usuarioSaveDTO) {
        usuarioService.save(usuarioSaveDTO);
        ResponseEntity.ok("Usuário cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.findUsuario()); //isso deve sair
    }

    @Operation(summary = "Atualiza o usuário logado")
    @PutMapping()
    public UsuarioDTO updateUsuario(@ModelAttribute @Valid UsuarioSaveDTO usuarioSaveDTO,
                                    @RequestPart(name = "img", required = false) MultipartFile img, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        String userId = usuarioService.getUsuarioLogadoId();
        return usuarioService.update(userId, usuarioSaveDTO, img);
    }

}
