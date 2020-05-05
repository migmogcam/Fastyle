package app.fastyleapplication.fastyle.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleapplication.fastyle.model.Esteticista;
import app.fastyleapplication.fastyle.model.ServicioEstetico;
import app.fastyleapplication.fastyle.model.Usuario;
import app.fastyleapplication.fastyle.services.EsteticistaService;
import app.fastyleapplication.fastyle.services.ServicioEsteticoService;
import app.fastyleapplication.fastyle.services.UsuarioService;

@Controller
public class ServicioEsteticoController {

	@Autowired
	ServicioEsteticoService service;

	@Autowired
	EsteticistaService serviceEsteticista;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	EsteticistaService esteticistaService;
	
	private static final Logger logger = Logger.getLogger(CitaController.class.getName());
	private static final String VIEW_SERVICIO_ESTETICO = "servicioEstetico";
	private static final String VIEW_ERROR = "viewError";
	private static final String VIEW_REDIRECT = "redirect:/";
	private static final String USER_ADMIN = "admin";
	private static final String LISTA_SERVICIOS = "listaServicios";
	private static final String VIEW_LISTADO = "listadoServicios";
	private static final String ROL_ADMIN = "ROLE_ADMIN";

	@GetMapping("/servicioEsteticoRegistro")
	public String addServicioEstetico(Model model) {
		model.addAttribute(VIEW_SERVICIO_ESTETICO, new ServicioEstetico());

		return "crearServicioEstetico";
	}

	@PostMapping("/crearServicioEstetico")
	public String addServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return VIEW_ERROR;
		}
		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (IllegalArgumentException e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
			return VIEW_ERROR;
		}
		return VIEW_REDIRECT;
	}

	@PostMapping("/servicioEsteticoUpdate/{id}")
	public String updateServicioEsteticoService(@PathVariable("id") Integer id,
			@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return VIEW_ERROR;
		}

		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (IllegalArgumentException e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
			return VIEW_ERROR;
		}
		String message1 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message1);
		return VIEW_REDIRECT;
	}

	@GetMapping("/servicioEsteticoDelete/{id}")
	public String deleteServicioEsteticoService(@PathVariable("id") Integer id, Model model) {
		try {
			service.deleteServicioEsteticoById(id);
		} catch (IllegalArgumentException e) {
			return VIEW_ERROR;
		}
		String message2 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message2);
		return VIEW_REDIRECT;
	}

		@GetMapping("/")
		public String listado(Model model) {
			List<ServicioEstetico> servicios;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			String provincia = "";
			String anonymousUser1 = "anonymousUser";
			if(!username.equals(anonymousUser1) && !username.equals(USER_ADMIN)) {
				Usuario u = this.usuarioService.findByUsuario(username);
				provincia = u.getProvincia();
			}
			try {
				if(!username.equals(anonymousUser1) && !username.equals(USER_ADMIN)) {
					servicios = service.getAllServicioEsteticosPorProvincia(provincia);
					model.addAttribute(LISTA_SERVICIOS, servicios);
				} else {
					servicios = service.getAllServicioEsteticos();
					model.addAttribute(LISTA_SERVICIOS, servicios);
				}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}

		@GetMapping("/mascotas")
		public String listadoMascotas(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(ROL_ADMIN));
			try {
				if (hasAdminRole) {

					serviciosTipo = service.getAllServicioEsteticosPorTipo("Mascotas");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);

				} else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Mascotas");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
				}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}
		
		@GetMapping("/tinte")
		public String listadoTinte(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(ROL_ADMIN));
			try {
				if(hasAdminRole) {				
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Tinte");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
					}else {
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Tinte");
				model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
			}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}
		
		@GetMapping("/pedicura-y-manicura")
		public String listadoPedicuraYManicura(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(ROL_ADMIN));
			try {
				if(hasAdminRole) {	
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Pedicura y Manicura");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);									
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Pedicura y Manicura");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
				}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}
				
		@GetMapping("/depilacion")
		public String listadoDepilacion(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(ROL_ADMIN));
			try {
				if(hasAdminRole) {			
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Depilacion");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);										
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Depilacion");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
				}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}
				
		@GetMapping("/peluqueria")
		public String listadoPeluqueria(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(ROL_ADMIN));
			try {
				if(hasAdminRole) {				
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Peluqueria");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);										
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Peluqueria");
					model.addAttribute(LISTA_SERVICIOS, serviciosTipo);
				}
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_LISTADO;
		}
				
		@GetMapping("/servicioInfo/{id}")
		public String diplayInfo(@PathVariable("id") long id, Model model) {
			List<Esteticista> servicios;
			ServicioEstetico servicioEstetico = new ServicioEstetico();
			String anonymousUser2 = "anonymousUser";
			try {
				servicioEstetico = service.getServicioEsteticoById((int) id);
				String anonimo = SecurityContextHolder.getContext().getAuthentication().getName();
				if (!anonimo.equals(anonymousUser2)) {
					Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
							.getAuthentication().getAuthorities();
					for (GrantedAuthority g : authorities) {
						if (g.getAuthority().equals("ROLE_ESTETICISTA")) {
							String username = SecurityContextHolder.getContext().getAuthentication().getName();
							Usuario u = this.usuarioService.findByUsuario(username);
							Esteticista c = this.esteticistaService.findByUsuario(u);
							if (servicioEstetico.getEsteticista().contains(c)) {
								model.addAttribute("est", "existe");
							} else {
								model.addAttribute("est", "noExiste");
							}
						} else {
							model.addAttribute("est", "noEs");
						}
					}
				} else {
					model.addAttribute("est", "noEs");
				}
				servicios = servicioEstetico.getEsteticista();
				model.addAttribute("listaEsteticistas", servicios);
				model.addAttribute(VIEW_SERVICIO_ESTETICO, servicioEstetico);
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return "servicioInfo";
		}

		@GetMapping("/ServicioUnir/{idServ}")
		public String servicioUnir(@PathVariable("idServ") long id, Model model) {
			ServicioEstetico servicioEstetico = new ServicioEstetico();
			try {
				servicioEstetico = service.getServicioEsteticoById((int) id);
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				Usuario u = this.usuarioService.findByUsuario(username);
				Esteticista c = this.esteticistaService.findByUsuario(u);
				servicioEstetico.getEsteticista().add(c);
				this.service.createOrUpdateServicioEstetico(servicioEstetico);
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_REDIRECT;
		}

		@GetMapping("/ServicioEditar/{idServ}")
		public String servicioEditar(@PathVariable("idServ") long id, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (username.equals(USER_ADMIN)) {
				ServicioEstetico servicioEstetico = new ServicioEstetico();
				try {
					servicioEstetico = service.getServicioEsteticoById((int) id);
					model.addAttribute(VIEW_SERVICIO_ESTETICO, servicioEstetico);
					return "editarServicio";
				} catch (IllegalArgumentException e) {
					logger.log(Logger.Level.FATAL, e.getMessage());
					return VIEW_ERROR;
				}
			} else {
				return VIEW_ERROR;
			}
		}
	
		@PostMapping("/editarServicioEstetico")
		public String editarServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result,
				Model model) {
			if (result.hasErrors()) {
				return VIEW_ERROR;
			}
			try {
				service.createOrUpdateServicioEstetico(servicioEstetico);
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_REDIRECT;
		}
	
		@GetMapping("/ServicioBorrar/{idServ}")
		public String servicioBorrar(@PathVariable("idServ") Integer id, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (username.equals(USER_ADMIN)) {
				ServicioEstetico servicioEstetico = new ServicioEstetico();
				try {
					service.deleteServicioEsteticoById(id);
					model.addAttribute(VIEW_SERVICIO_ESTETICO, servicioEstetico);
					return VIEW_REDIRECT;
				} catch (IllegalArgumentException e) {
					logger.log(Logger.Level.FATAL, e.getMessage());
					return VIEW_ERROR;
				}
			} else {
				return VIEW_ERROR;
			}
		}
	
		@GetMapping("/ServicioCancelar/{idServ}")
		public String servicioCancelar(@PathVariable("idServ") long id, Model model) {
			ServicioEstetico servicioEstetico = new ServicioEstetico();
			try {
				servicioEstetico = service.getServicioEsteticoById((int) id);
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				Usuario u = this.usuarioService.findByUsuario(username);
				Esteticista c = this.esteticistaService.findByUsuario(u);
				servicioEstetico.getEsteticista().remove(c);
				this.service.createOrUpdateServicioEstetico(servicioEstetico);
			} catch (IllegalArgumentException e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return VIEW_ERROR;
			}
			return VIEW_REDIRECT;
		}
}
