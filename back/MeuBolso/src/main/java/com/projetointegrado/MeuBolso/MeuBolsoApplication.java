package com.projetointegrado.MeuBolso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
@SpringBootApplication
public class MeuBolsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuBolsoApplication.class, args);
	}

}
