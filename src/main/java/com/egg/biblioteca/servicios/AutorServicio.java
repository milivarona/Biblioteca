package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;


    @Transactional
    public void crearAutor(String nombre) throws Exception{
        validar(nombre);
        Autor autor = new Autor();// Instancio un objeto del tipo Autor
        autor.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro
        autorRepositorio.save(autor); // Persisto el dato en mi BBDD
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<>();

        autores = autorRepositorio.findAll();

        return autores;
    }

    public void validar(String nombre) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre de la editorial no puede estar vacío o ser nulo");
        }
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws Exception {
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (!respuesta.isPresent()) {
            throw new MiException("El autor no existe.");
        }
        Autor autor = respuesta.get();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.getReferenceById(id);
    }
}