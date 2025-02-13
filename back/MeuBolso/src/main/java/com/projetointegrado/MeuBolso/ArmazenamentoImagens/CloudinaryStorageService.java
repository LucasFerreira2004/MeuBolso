package com.projetointegrado.MeuBolso.ArmazenamentoImagens;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.Map;

public class CloudinaryStorageService implements IStorageService {
    private final Dotenv dotenv = Dotenv.load();
    private Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));



    @Override
    public String uploadFile(MultipartFile file) {
        @Override
        public String uploadFile(MultipartFile file) {
            try {
                // 1️⃣ Converte a imagem para BufferedImage
                InputStream inputStream = file.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);

                // 2️⃣ Converte para WEBP e reduz tamanho (exemplo: largura máx de 800px)
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(image)
                        .size(800, 800) // Redimensiona para no máximo 800x800
                        .outputFormat("webp") // Converte para WEBP
                        .outputQuality(0.8) // Ajusta qualidade
                        .toOutputStream(outputStream);

                // 3️⃣ Faz o upload para o Cloudinary
                Map uploadResult = cloudinary.uploader().upload(
                        outputStream.toByteArray(),
                        ObjectUtils.asMap("resource_type", "image", "format", "webp")
                );

                // 4️⃣ Retorna a URL da imagem no Cloudinary
                return (String) uploadResult.get("secure_url");

            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem!", e);
            }
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }

    public void setCloudinary(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
}
