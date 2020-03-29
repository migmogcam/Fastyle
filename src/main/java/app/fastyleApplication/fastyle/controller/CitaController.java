package app.fastyleApplication.fastyle.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.dto.CitaDTO;
import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.services.CitaService;
import app.fastyleApplication.fastyle.services.ClienteService;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.ServicioEsteticoService;

@Controller
public class CitaController {
	
	@Autowired
	CitaService service;
	
	@Autowired
	ServicioEsteticoService serviceServicio;
	
	@Autowired
	EsteticistaService serviceEsteticista;
	
	@Autowired
	ClienteService clienteService;
	
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
	
	@GetMapping("/citaCrear/{idServ}/{idEst}")
    public String diplayInfo(@PathVariable("idServ") long id, @PathVariable("idEst") long idE, Model model, HttpSession session) {
		Esteticista servicios = null;
		ServicioEstetico servicioEstetico = null;
		try {
			servicios = serviceEsteticista.getEsteticistaById((int) idE);
			servicioEstetico = serviceServicio.getServicioEsteticoById((int) id);
			session.setAttribute("servicioEstetico", servicioEstetico);
			session.setAttribute("servicios", servicios);
			CitaDTO cita = new CitaDTO();
			model.addAttribute("cita", cita);
		} catch (Exception e) {
			return "index"; 
		}
		
        return "citaCrear"; //view
    }

	@GetMapping("/misCitas")
    public String misCitas(final Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsuario(username);
		List<Cita> citas = new LinkedList<Cita>();
		citas = c.getCitas();
		model.put("citas", citas);
        return "misCitas"; //view
    }


}
