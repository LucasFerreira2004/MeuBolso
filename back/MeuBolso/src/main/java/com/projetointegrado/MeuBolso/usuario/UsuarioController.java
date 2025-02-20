package com.projetointegrado.MeuBolso.usuario;

import com.projetointegrado.MeuBolso.globalExceptions.ValoresNaoPermitidosException;
import com.projetointegrado.MeuBolso.usuario.dto.OnCreate;
import com.projetointegrado.MeuBolso.usuario.dto.OnUpdate;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @GetMapping
    public UsuarioDTO findUsuario(){
        String userId = usuarioService.getUsuarioLogadoId();
        return usuarioService.findById(userId);
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Validated(OnCreate.class) UsuarioSaveDTO usuarioSaveDTO, BindingResult bindingResult) throws ValoresNaoPermitidosException {
        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }
        usuarioService.save(usuarioSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
    }

    @PutMapping
    public UsuarioDTO updateUsuario(
            @ModelAttribute @Validated(OnUpdate.class) UsuarioSaveDTO usuarioSaveDTO,
            @RequestPart(name = "img", required = false) MultipartFile img,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValoresNaoPermitidosException(bindingResult);
        }

        String userId = usuarioService.getUsuarioLogadoId();
        return usuarioService.update(userId, usuarioSaveDTO, img);
    }
}
