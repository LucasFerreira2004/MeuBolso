package com.projetointegrado.MeuBolso.ArmazenamentoImagens;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CloudinaryStorageService implements IStorageService {
    private final Dotenv dotenv = Dotenv.load();
    private Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

    @Override
    public String uploadFile(MultipartFile file) {
            try {
                InputStream inputStream = file.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);

                // Converte para WEBP e reduz tamanho
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(image)
                        .size(250, 250) // Redimensiona para no máximo 800x800
                        .outputFormat("webp") // Converte para WEBP
                        .outputQuality(0.8) // Ajusta qualidade
                        .toOutputStream(outputStream);

                // Faz o upload para o Cloudinary
                Map uploadResult = cloudinary.uploader().upload(
                        outputStream.toByteArray(),
                        ObjectUtils.asMap("resource_type", "image",
                                                    "format", "webp")
                );

                // Retorna a URL da imagem no Cloudinary
                return (String) uploadResult.get("secure_url");

            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem!", e);
            }
    }

}
