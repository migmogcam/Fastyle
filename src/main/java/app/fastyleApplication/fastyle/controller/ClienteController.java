package app.fastyleApplication.fastyle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.services.ClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	@PostMapping("/clienteRegistro")
    public String addCliente(@Valid Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        } 
        try {
			service.createOrUpdateCliente(cliente);
		} catch (Exception e) {
			e.printStackTrace();
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
    }
	
	@GetMapping("/clienteEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    try {
			Cliente cliente = service.getClienteById(id);
		} catch (Exception e) {
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
	}
	
	@PostMapping("/clienteUpdate/{id}")
	public String updateCliente(@PathVariable("id") Integer id, @Valid Cliente cliente, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
            return "error";
	    }
	         
	    try {
			service.createOrUpdateCliente(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
	}

}
