package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") // localhost:8080/libro/registrar
    public String registrar(ModelMap model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelMap) throws Exception {
        try {

            // 🔍 Verificando qué valores llegan al controlador
            System.out.println("----- DATOS RECIBIDOS -----");
            System.out.println("ISBN: " + isbn);
            System.out.println("Título: " + titulo);
            System.out.println("Ejemplares: " + ejemplares);
            System.out.println("ID Autor: " + idAutor);
            System.out.println("ID Editorial: " + idEditorial);
            System.out.println("----------------------------");

            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelMap.addAttribute("exito", "Libro creado con éxito!");
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelMap.put("error", "Ocurrió un error");
            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
        Libro libro = libroServicio.getOne(isbn);
        modelo.put("libro", libroServicio.getOne(isbn));
        modelo.addAttribute("autores", autorServicio.listarAutores()); // Agregar autores para que nos muestre la lista
                                                                       // de autores
        modelo.addAttribute("editoriales", editorialServicio.listarEditoriales()); // Agregar editoriales para que nos
                                                                                   // muestre la lista de autores

        modelo.addAttribute("autorSeleccionado", libro.getAutor().getId()); // UUID del autor actual para que ya
                                                                            // aparezca seleccionado
        modelo.addAttribute("editorialSeleccionada", libro.getEditorial().getId()); // UUID de la editorial actual para
                                                                                    // que ya aparezca seleccionado
        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo)
            throws Exception {
        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:/libro/lista";
        } catch (MiException ex) {
            modelo.addAttribute("autores", autorServicio.listarAutores());
            modelo.addAttribute("editoriales", editorialServicio.listarEditoriales());
            modelo.put("error", ex.getMessage());
            return "libro_modificar.html";
        }
    }
}