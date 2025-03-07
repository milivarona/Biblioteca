package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

  
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws Exception {
        Libro libro = new Libro();
        if (isbn == null) {
            throw new MiException("Isbn es nulo.");
        }
        if (titulo == null) {
            throw new MiException("titulo es nulo.");
        }
        if (idAutor == null) {
            throw new MiException("autor ID es nulo.");
        }
        if (idEditorial == null) {
            throw new MiException("editorial ID es nulo.");
        }
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        validar(titulo, ejemplares);
        libro.setAlta(new Date());
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }

    @Transactional(readOnly=true)
    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws Exception {
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        validar(titulo, ejemplares);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        if (respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            if(respuestaAutor.isPresent()){
                Autor autor = respuestaAutor.get();
                autor.setId(idAutor);
            }
            if(respuestaEditorial.isPresent()){
                Editorial editorial = respuestaEditorial.get();
                editorial.setId(idEditorial);
            }
            libroRepositorio.save(libro);
        }
    }
    private void validar(String titulo, Integer ejemplares) throws Exception{
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título no puede estar vacío o nulo");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new MiException("El número de ejemplares debe ser mayor a 0");
        }
    }

    @Transactional(readOnly = true)
    public Libro getOne(Long isbn) {
        return libroRepositorio.getReferenceById(isbn);
    }
}
