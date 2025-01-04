package com.projetointegrado.MeuBolso.autorizacao.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationExecptionHandler {

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> handleInternalAuthenticationServiceException (InternalAuthenticationServiceException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("email", "Email não cadastrado");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException (BadCredentialsException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("senha", "Senha incorreta");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler (JWTCreationException.class)
    public ResponseEntity<?> handleJWTCreationException (JWTCreationException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("", "erro ao gerar o token");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
