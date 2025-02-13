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
        long maxSize = 2 * 1024 * 1024; // 2MB em bytes
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("A imagem excede o tamanho máximo permitido de 2MB.");
        }

            try {
                InputStream inputStream = file.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);
                String contentType = file.getContentType();
                if (contentType == null || (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
                    throw new IllegalArgumentException("Apenas arquivos PNG e JPG são permitidos!");
                }

                // Converte para JPG e reduz tamanho
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(image)
                        .size(250, 250) // Redimensiona para no máximo 800x800
                        .outputFormat("jpg") // Converte para WEBP
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
                e.printStackTrace();
                throw new RuntimeException("Erro ao fazer upload da imagem!", e);
            }
    }

}
