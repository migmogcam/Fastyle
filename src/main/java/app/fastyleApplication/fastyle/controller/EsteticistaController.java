package app.fastyleApplication.fastyle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.services.EsteticistaService;

@Controller
public class EsteticistaController {
	
	@Autowired
	EsteticistaService service;
	
	@PostMapping("/esteticistaRegistro")
    public String addEsteticista(@Valid Esteticista esteticista, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        } 
        try {
			service.createOrUpdateEsteticista(esteticista);
		} catch (Exception e) {
			e.printStackTrace();
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
    }
	
	@GetMapping("/esteticistaEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    try {
			Esteticista esteticista = service.getEsteticistaById(id);
		} catch (Exception e) {
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
	}
	
	@PostMapping("/esteticistaUpdate/{id}")
	public String updateEsteticista(@PathVariable("id") Integer id, @Valid Esteticista esteticista, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
            return "error";
	    }
	         
	    try {
			service.createOrUpdateEsteticista(esteticista);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
	}

}
