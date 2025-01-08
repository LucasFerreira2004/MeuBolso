package com.projetointegrado.MeuBolso.usuario.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UsuarioExceptionHandler {

    // Tratamento para campos inválidos ou não preenchidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        return ResponseEntity.badRequest().body(erros);
    }

    // Tratamento para usuários duplicados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateDataException(DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Usuário com esses dados já cadastrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // Retorna 409
    }

    // Tratamento para email já cadastrado usado no cadastro
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<?> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("email", ex.getMessage());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }
    // Tratamento para usuario invalido
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("email", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Tratamento para senha incorreta
    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<ErrorResponseDTO> handleSenhaIncorretaException(SenhaIncorretaException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("senha", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Tratamento genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Ocorreu um erro interno no servidor.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500
    }
}
