package com.projetointegrado.MeuBolso.armazenamentoImagens;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    String uploadFile(MultipartFile file);
}

