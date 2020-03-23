package app.fastyleApplication.fastyle.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.ServicioEsteticoService;

@Controller
public class ServicioEsteticoController {
	
	@Autowired
	ServicioEsteticoService service;
	
	@Autowired
	EsteticistaService serviceEsteticista;
	
	@PostMapping("/servicioEsteticoRegistro")
    public String addServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	//TODO añadir vista errores
            return "vista de errores";
        } 
        try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
        	//TODO añadir vista errores
            return "vista de errores";
		}
        //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
    }
	
	@GetMapping("/servicioEsteticoEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    try {
			ServicioEstetico servicioEstetico = service.getServicioEsteticoById(id);
		} catch (Exception e) {
        	//TODO añadir vista errores
            return "vista de errores";
		}
	     
        //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@PostMapping("/servicioEsteticoUpdate/{id}")
	public String updateServicioEsteticoService(@PathVariable("id") Integer id, @Valid ServicioEstetico servicioEstetico, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
        	//TODO añadir vista errores
            return "vista de errores";
	    }
	         
	    try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@GetMapping("/servicioEsteticoDelete/{id}")
	public String deleteServicioEsteticoService(@PathVariable("id") Integer id, Model model) {
	    try {
			service.deleteServicioEsteticoById(id);
		} catch (Exception e) {
        	//TODO añadir vista errores
            return "vista de errores";
		}
		  //TODO Añadir vista de creacion correcta
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "vista todo OK";
	}
	
	@GetMapping("/listadoServicios")
    public String listado(Model model) {
		List<ServicioEstetico> servicios = null;
		try {
			servicios = service.getAllServicioEsteticos();
			model.addAttribute("listaServicios", servicios);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "listadoServicios"; //view
    }

	@GetMapping("/servicioInfo/{id}")
    public String diplayInfo(@PathVariable("id") long id, Model model) {
		List<Esteticista> servicios = null;
		ServicioEstetico servicioEstetico = null;
		try {
			servicios = serviceEsteticista.getAllEsteticistas();
			servicioEstetico = service.getServicioEsteticoById((int) id);
			model.addAttribute("listaEsteticistas", servicios);
			model.addAttribute("servicioEstetico", servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "servicioInfo"; //view
    }

}
