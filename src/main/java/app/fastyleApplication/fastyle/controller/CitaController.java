package app.fastyleApplication.fastyle.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
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
	
	private static final Logger logger = Logger.getLogger(CitaController.class.getName());
	private static final String VIEW_ERROR = "error";
	private static final String CITAS_ESTETICISTA = "citasEsteticista";
	private static final String VIEW_CITAS = "citas";
	private static final String EMPTY_CITAS = "emptyCitas";
	private static final String MIS_CITAS = "misCitas";
	private static final String ROL_CLIENTE = "ROLE_CLIENTE";
	
	
	
	
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
			logger.log(Logger.Level.FATAL, e.getMessage());
            return VIEW_ERROR;
		}
        return "redirect:/";
    }
	
	@PostMapping("/citaUpdate/{id}")
	public String updateCitaService(@PathVariable("id") Integer id, @Valid Cita cita, 
	  BindingResult result, Model model) {
		String view1 = "accionRealizada";
	    if (result.hasErrors()) {
            return VIEW_ERROR;
	    }
	         
	    try {
			service.createOrUpdateCita(cita);
		} catch (Exception e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
			return VIEW_ERROR;
		}
	    String message = "Añadir lo que se necesite en la vista a la que se va redirigir";
        model.addAttribute("message", message);
        return view1;
	}
	
	@GetMapping("/citaDelete/{id}")
	public String deleteCitaService(@PathVariable("id") Integer id, Model model) {
		String view2 = "accionRealizada";
		try {
			service.deleteCitaById(id);
		} catch (Exception e) {
            return VIEW_ERROR;
		}
		String message = "Añadir lo que se necesite en la vista a la que se va redirigir";
        model.addAttribute("message", message);
        return view2;
	}
	
	@GetMapping("/citaCrear/{idServ}/{idEst}")
    public String diplayInfo(@PathVariable("idServ") long id, @PathVariable("idEst") long idE, Model model, HttpSession session, HttpServletRequest request) {
		Esteticista servicios;
		ServicioEstetico servicioEstetico;
		Boolean fallo = false;
		String fallo1 = "fallo";
		if(request.getSession().getAttribute(fallo1) != null) {
			fallo = (Boolean) request.getSession().getAttribute(fallo1);
		}
		try {
			servicios = serviceEsteticista.getEsteticistaById((int) idE);
			servicioEstetico = serviceServicio.getServicioEsteticoById((int) id);
			session.setAttribute("servicioEstetico", servicioEstetico);
			session.setAttribute("servicios", servicios);
			CitaDTO cita = new CitaDTO();
			model.addAttribute(fallo1, fallo);
			model.addAttribute("cita", cita);
		} catch (Exception e) {
			return "index"; 
		}
        return "citaCrear";
    }
	
	@GetMapping("/verCita/{idCita}")
    public String diplayCita(@PathVariable("idCita") Integer id,Model model, HttpSession session, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isCliente = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));
		try {
			Cita cita = this.service.getCitaById(id);
			model.addAttribute("cita", cita);
			model.addAttribute("isCliente", isCliente);
		} catch (Exception e) {
			return VIEW_ERROR;
		}
		return "verCita";
    }

	@GetMapping("/misCitas")
    public String misCitas(final Map<String, Object> model, Model model2) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Cliente c = this.clienteService.findByUsuario(u);
		List<Cita> citas;	
		citas = c.getCitas();
		
		if(citas.isEmpty()) {
			model2.addAttribute("citasVacio", true);
			model2.addAttribute(CITAS_ESTETICISTA, false);
			return EMPTY_CITAS;
		} else {
			model.put(VIEW_CITAS, citas);
			model.put(CITAS_ESTETICISTA, false);
	        return MIS_CITAS;
		}
    }

	@GetMapping("/citasEsteticista/{idEst}")
    public String citasEsteticista(@PathVariable("idEst") Integer id, final Map<String, Object> model, Model model2) {
		Esteticista esteticista = new Esteticista();
		try {
			esteticista = this.esteticistaService.getEsteticistaById(id);
		} catch (Exception e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
		}
		List<Cita> citas;
		citas = esteticista.getCitas();
		
		if(citas.isEmpty()) {
			model2.addAttribute("citasVacio", true);
			model2.addAttribute(CITAS_ESTETICISTA, true);
			return EMPTY_CITAS;
		} else {
			model.put(VIEW_CITAS, citas);
			model.put("esteticista", citas.get(0).getEsteticista());
			model.put(CITAS_ESTETICISTA, true);
	        return MIS_CITAS;
		}
    }
	
	@GetMapping("/misCitasEsteticista")
    public String misCitasEsteticista(final Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Esteticista c = this.esteticistaService.findByUsuario(u);
		List<Cita> citas;
		citas = c.getCitas();
		
		if(citas.isEmpty()) {
			model.put(CITAS_ESTETICISTA, false);
			return EMPTY_CITAS;
		} else {
			model.put(VIEW_CITAS, citas);
			model.put(CITAS_ESTETICISTA, false);
	        return MIS_CITAS;
		}
    }
	
	@GetMapping("/positiva/{idCita}")
    public String positiva(@PathVariable("idCita") Integer id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isCliente = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));

		try {
			Cita cita = this.service.getCitaById(id);
			if(isCliente && !cita.getValorar()) {
			cita.setValorar(true);
			Esteticista este = this.esteticistaService.getEsteticistaById(cita.getEsteticista().getId());
			Integer valor = este.getPositivo() +1;
			este.setPositivo(valor);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalDate ahora = LocalDate.now();
			String tAhora = LocalTime.now().format(dtf);
			cita.setFValorar(ahora.toString()+" "+tAhora.toString());
			this.service.createOrUpdateCita(cita);
			this.serviceEsteticista.createOrUpdateCliente(este);
			}else {
				return VIEW_ERROR;
			}
		} catch (Exception e) {
			return VIEW_ERROR;
		}
		return "redirect:/misCitas";

	}
	
	@GetMapping("/negativa/{idCita}")
    public String negativa(@PathVariable("idCita") Integer id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isCliente = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));
		try {
			Cita cita = this.service.getCitaById(id);
			if(isCliente && !cita.getValorar()) {
			cita.setValorar(true);
			Esteticista este = this.esteticistaService.getEsteticistaById(cita.getEsteticista().getId());
			Integer valor = este.getNegativo() +1;
			este.setNegativo(valor);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalDate ahora = LocalDate.now();
			String tAhora = LocalTime.now().format(dtf);
			cita.setFValorar(ahora.toString()+" "+tAhora.toString());
			this.service.createOrUpdateCita(cita);
			this.serviceEsteticista.createOrUpdateCliente(este);
			}else {
				return VIEW_ERROR;
			}
		} catch (Exception e) {
			return VIEW_ERROR;
		}
		return "redirect:/misCitas";
	}
	
}
