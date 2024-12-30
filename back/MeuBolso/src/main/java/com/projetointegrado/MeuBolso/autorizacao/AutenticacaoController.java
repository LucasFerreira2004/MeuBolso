package com.projetointegrado.MeuBolso.autorizacao;

import com.projetointegrado.MeuBolso.autorizacao.dto.CadastroDTO;
import com.projetointegrado.MeuBolso.autorizacao.dto.LoginDTO;
import com.projetointegrado.MeuBolso.autorizacao.dto.LoginResponseDTO;
import com.projetointegrado.MeuBolso.autorizacao.token.TokenService;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;
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
    private TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginDTO data) {
        var usernameSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        System.out.println("email: " + data.email() + " senha: " + data.senha());
        System.out.println(usernameSenha);
        var auth = this.authenticationManager.authenticate(usernameSenha);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return new LoginResponseDTO(token); //poderia ser só .ok.build()
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroDTO data){
        UsuarioDTO usuarioDTO = new UsuarioDTO(data.nome(), data.email(), data.senha());
        usuarioService.salvarUsuario(usuarioDTO);

        return ResponseEntity.ok().build();
    }
}
