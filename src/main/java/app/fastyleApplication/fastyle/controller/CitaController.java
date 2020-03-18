package app.fastyleApplication.fastyle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.services.CitaService;

@Controller
public class CitaController {
	
	@Autowired
	CitaService service;
	
	@PostMapping("/citaRegistro")
    public String addCita(@Valid Cita cita, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	//TODO añadir vista errores
            return "vista de errores";
        } 
        try {
			service.createOrUpdateCita(cita);
		} catch (Exception e) {
			e.printStackTrace();
        	//TODO añadir vista errores
            return "vista de errores";
		}
        //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
    }
	
	@GetMapping("/citaEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    try {
			Cita cita = service.getCitaById(id);
		} catch (Exception e) {
        	//TODO añadir vista errores
            return "vista de errores";
		}
	     
        //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@PostMapping("/citaUpdate/{id}")
	public String updateCitaService(@PathVariable("id") Integer id, @Valid Cita cita, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
        	//TODO añadir vista errores
            return "vista de errores";
	    }
	         
	    try {
			service.createOrUpdateCita(cita);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@GetMapping("/citaDelete/{id}")
	public String deleteCitaService(@PathVariable("id") Integer id, Model model) {
	    try {
			service.deleteCitaById(id);
		} catch (Exception e) {
        	//TODO añadir vista errores
            return "vista de errores";
		}
		  //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@GetMapping("/citaCrear")
    public String diplayInfo(Model model) {
        return "citaCrear"; //view
    }

	@GetMapping("/misCitas")
    public String misCitas(Model model) {
        return "misCitas"; //view
    }


}
