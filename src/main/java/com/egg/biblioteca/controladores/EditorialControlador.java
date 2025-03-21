package com.egg.biblioteca.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelMap) throws Exception {
        try {
            editorialServicio.crearEditorial(nombre);
            modelMap.addAttribute("Éxito", "Editorial creado con éxito!");            
        } catch (MiException e) {
            modelMap.addAttribute("Error", "Ocurrió un error");
        }
        return "editorial_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", editorialServicio.getOne(id));
        return "editorial_modificar.html";
    }


    @PostMapping("{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            editorialServicio.modificarEditorial(id,nombre);
            return "editorial_list.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
    
}
