package com.egg.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;

@Controller
@RequestMapping("/autor") // localhost:8080/autor
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelMap) {
        try {
            autorServicio.crearAutor(nombre);
            modelMap.addAttribute("Éxito", "Autor creado con éxito!");
        } catch (MiException e) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, e);
            modelMap.addAttribute("Error", "Ocurrió un error");           
        }
        return "autor_form.html";
    }

}
