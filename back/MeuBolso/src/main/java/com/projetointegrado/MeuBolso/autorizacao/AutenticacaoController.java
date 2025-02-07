package com.projetointegrado.MeuBolso.autorizacao;

import com.projetointegrado.MeuBolso.autorizacao.dto.CadastroDTO;
import com.projetointegrado.MeuBolso.autorizacao.dto.LoginDTO;
import com.projetointegrado.MeuBolso.autorizacao.dto.LoginResponseDTO;
import com.projetointegrado.MeuBolso.autorizacao.token.TokenService;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import com.projetointegrado.MeuBolso.usuario.Usuario;
import com.projetointegrado.MeuBolso.usuario.dto.UsuarioSaveDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("usuarioService")
    private IUsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginDTO data) {
        var usernameSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        System.out.println("email: " + data.email() + " senha: " + data.senha());
        System.out.println(usernameSenha);
        var auth = this.authenticationManager.authenticate(usernameSenha);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroDTO data){
        UsuarioSaveDTO usuarioSaveDTO = new UsuarioSaveDTO(data.nome(), data.email(), data.senha());
        usuarioService.save(usuarioSaveDTO);

        return ResponseEntity.ok().build();
    }
}
