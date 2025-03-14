package com.projetointegrado.MeuBolso.usuario.dto;

import com.projetointegrado.MeuBolso.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioSaveDTO {

    @NotBlank(message = "O nome é obrigatório.", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.", groups = {OnCreate.class, OnUpdate.class})
    private String nome;

    @NotBlank(message = "O email é obrigatório.", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email inválido.", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    // Aqui a senha é obrigatória APENAS no create
    @NotBlank(message = "A senha é obrigatória.", groups = OnCreate.class)
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.", groups = OnCreate.class)
    private String senha;

    public UsuarioSaveDTO() {
    }

    public UsuarioSaveDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }

    public UsuarioSaveDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}