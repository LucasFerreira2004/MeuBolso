package com.projetointegrado.MeuBolso.ArmazenamentoImagens;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    String uploadFile(MultipartFile file);
}

