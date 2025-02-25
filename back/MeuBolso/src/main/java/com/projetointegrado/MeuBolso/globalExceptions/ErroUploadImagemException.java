package com.projetointegrado.MeuBolso.globalExceptions;

public class ErroUploadImagemException extends RuntimeException {
    public ErroUploadImagemException(String message) {
        super(message);
    }
    public ErroUploadImagemException() {super("erro ao fazer o upload da imagem. O formato deve ser pgn e o arquivo não deve exceder 2MB");}
}
