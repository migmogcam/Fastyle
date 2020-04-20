package app.fastyleApplication.fastyle.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.services.CitaService;
import app.fastyleApplication.fastyle.services.ClienteService;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.ServicioEsteticoService;
import app.fastyleApplication.fastyle.services.UsuarioService;

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


	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	EsteticistaService esteticistaService;
	
	@PostMapping("/guardarRespuesta")
    public String addCita(@Valid Cita cita, BindingResult result, Model model) {
        try {
        	LocalDate ahora = LocalDate.now();
    		LocalTime tAhora = LocalTime.now();
    		Integer año = ahora.getYear();
    		Integer mes = ahora.getMonthValue();
    		Integer dia = ahora.getDayOfMonth();
    		Integer hora = tAhora.getHour();
    		Integer minuto = tAhora.getMinute();
    		String stringAño = año.toString();
    		String stringMes = mes.toString();
    		String stringDia = dia.toString();
    		String stringHora = hora.toString();
    		String stringMinuto = minuto.toString();
    		if (mes < 10) {
    			stringMes = "0" + stringMes;
    		}
    		if (dia < 10) {
    			stringDia = "0" + stringDia;
    		}
    		if (hora < 10) {
    			stringHora = "0" + stringHora;
    		}
    		if (minuto < 10) {
    			stringMinuto = "0" + stringMinuto;
    		}
    		String momento = stringAño + "-" + stringMes + "-" + stringDia + " " + stringHora + ":" + stringMinuto;
    		cita.setMomento(momento);
			service.createOrUpdateCita(cita);
		} catch (Exception e) {
			e.printStackTrace();
            return "error";
		}
        return "redirect:/";
    }
	
	
	@GetMapping("/citaEdit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    try {
			Cita cita = service.getCitaById(id);
		} catch (Exception e) {
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "redirect:/";
	}
	
	@PostMapping("/citaUpdate/{id}")
	public String updateCitaService(@PathVariable("id") Integer id, @Valid Cita cita, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
            return "error";
	    }
	         
	    try {
			service.createOrUpdateCita(cita);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "redirect:/";
	}
	
	@GetMapping("/citaDelete/{id}")
	public String deleteCitaService(@PathVariable("id") Integer id, Model model) {
	    try {
			service.deleteCitaById(id);
		} catch (Exception e) {
            return "error";
		}
        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
        return "accionRealizada";
	}
	
	@GetMapping("/citaCrear/{idServ}/{idEst}")
    public String diplayInfo(@PathVariable("idServ") long id, @PathVariable("idEst") long idE, Model model, HttpSession session, HttpServletRequest request) {
		Esteticista servicios = null;
		ServicioEstetico servicioEstetico = null;
		Boolean fallo = false;
		if(request.getSession().getAttribute("fallo") != null) {
			fallo = (Boolean) request.getSession().getAttribute("fallo");
		}
		try {
			servicios = serviceEsteticista.getEsteticistaById((int) idE);
			servicioEstetico = serviceServicio.getServicioEsteticoById((int) id);
			session.setAttribute("servicioEstetico", servicioEstetico);
			session.setAttribute("servicios", servicios);
			CitaDTO cita = new CitaDTO();
			model.addAttribute("fallo", fallo);
			model.addAttribute("cita", cita);
		} catch (Exception e) {
			return "index"; 
		}
		
        return "citaCrear"; //view
    }
	
	

	/*   Si me logeo con un cliente y voy a los detalles
	 *  de una cita mia, si pongo la id de otra cita que no es mia, 
	 *  me deja ver los detalles de esa cita. */
	
	
	@GetMapping("/verCita/{idCita}")
    public String diplayCita(@PathVariable("idCita") Integer id,Model model, HttpSession session, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isCliente = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));
		
		// usuario logueado
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		// cliente de la cita
		
		try {
			
			Usuario userCliente =    this.service.getCitaById(id).getCliente().getUsuario();
			Usuario userEsteticista =  this.service.getCitaById(id).getEsteticista().getUsuario();
			if(userCliente.equals(u) || userEsteticista.equals(u)) {
			
			Cita cita = this.service.getCitaById(id);
			model.addAttribute("cita", cita);
			model.addAttribute("isCliente", isCliente);
			}
		} catch (Exception e) {
			return "error";
		}
		return "verCita";
    }

	@GetMapping("/misCitas")
    public String misCitas(final Map<String, Object> model, Model model2) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Cliente c = this.clienteService.findByUsuario(u);
		List<Cita> citas = new LinkedList<Cita>();
		citas = c.getCitas();
		
		if(citas.isEmpty()) {
			model2.addAttribute("citasVacio", true);
			return "emptyCitas";
		} else {
			model.put("citas", citas);
	        return "misCitas"; //view
		}
    }
	
	@GetMapping("/misCitasEsteticista")
    public String misCitasEsteticista(final Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Esteticista c = this.esteticistaService.findByUsuario(u);
		List<Cita> citas = new LinkedList<Cita>();
		citas = c.getCitas();
		
		if(citas.isEmpty()) {
			return "emptyCitas";
		} else {
			model.put("citas", citas);
	        return "misCitas"; //view
		}
    }


}
