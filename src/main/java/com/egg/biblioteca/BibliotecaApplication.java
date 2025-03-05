package com.egg.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.egg.biblioteca.excepciones.MiException;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) throws MiException {
		SpringApplication.run(BibliotecaApplication.class, args);
		// AutorServicio autor1 = new AutorServicio();
		// AutorServicio autor = SpringApplication.run(BibliotecaApplication.class, args).getBean(AutorServicio.class);
		// autor1.crearAutor("Gabriel Rolón");
		
	}

}
