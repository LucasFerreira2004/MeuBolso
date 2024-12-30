package com.projetointegrado.MeuBolso.autorizacao;

import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/auth")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data){
        var usernameSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        System.out.println("email: " + data.email() + " senha: " + data.senha());
        System.out.println(usernameSenha);
        var auth = this.authenticationManager.authenticate(usernameSenha);

        return ResponseEntity.ok().build(); //poderia ser só .ok.build()
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroDTO data){
        UsuarioDTO usuarioDTO = new UsuarioDTO(data.nome(), data.email(), data.senha());
        usuarioService.salvarUsuario(usuarioDTO);

        return ResponseEntity.ok().build();
    }
}
