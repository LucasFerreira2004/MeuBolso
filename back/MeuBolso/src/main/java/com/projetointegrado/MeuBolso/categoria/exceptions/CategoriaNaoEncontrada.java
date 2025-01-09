package com.projetointegrado.MeuBolso.categoria.exceptions;

public class CategoriaNaoEncontrada extends RuntimeException {
    public CategoriaNaoEncontrada(String message) {
        super(message);
    }
  public CategoriaNaoEncontrada() {
    super("categoria nao encontrada");
  }

}
