package com.egg.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.egg.biblioteca.excepciones.MiException;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) throws MiException {
		SpringApplication.run(BibliotecaApplication.class, args);
		
	}

}
