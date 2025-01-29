package com.projetointegrado.MeuBolso.conta.exception;

import com.projetointegrado.MeuBolso.banco.exception.BancoNaoEncontradoException;
import com.projetointegrado.MeuBolso.tipoConta.exception.TipoContaNaoEncontradoException;
import com.projetointegrado.MeuBolso.usuario.exception.ErrorResponseDTO;
import com.projetointegrado.MeuBolso.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContaExceptionHandler {
    @ExceptionHandler(BancoNaoEncontradoException.class)
    public ResponseEntity<?> handleIdBancoNaoEncontradoException(BancoNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("id_banco", "id banco nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoContaNaoEncontradoException.class)
    public ResponseEntity<?> handleIdTipoContaNaoEncontradoException(TipoContaNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("id_tipo_conta", "id tipo conta nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<?> handleIdUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("token", "id usuario nao encontrado a partir do token");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<?> handleIdContaNaoEncontradaException(ContaNaoEncontradaException ex) {
        IdNaoEncontradoDTO dto = new IdNaoEncontradoDTO("/id", "id conta nao encontrado");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DescricaoJaExistenteException.class)
    public ResponseEntity<?> handleDescricaoJaExistenteException(DescricaoJaExistenteException ex) {
        ErrorResponseDTO dto = new ErrorResponseDTO("descricao", "descricao ja existente, insira outra descricao");
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
}
