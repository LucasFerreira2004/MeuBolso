package com.projetointegrado.MeuBolso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MeuBolsoApplication {

	public static void main(String[] args) {

		System.out.println(System.getenv("CLOUDINARY_URL"));
		SpringApplication.run(MeuBolsoApplication.class, args);
	}

}
