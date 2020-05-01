package app.fastyleApplication.fastyle.controller;

import java.util.ArrayList;
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

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.ServicioEsteticoService;
import app.fastyleApplication.fastyle.services.UsuarioService;

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
	private static final String viewServicioEstetico = "servicioEstetico";
	private static final String viewError = "viewError";
	private static final String redirect = "redirect:/";
	private static final String admin = "admin";
	private static final String listaServicios = "listaServicios";
	private static final String viewListado = "listadoServicios";
	private static final String rolAdmin = "ROLE_ADMIN";

	@GetMapping("/servicioEsteticoRegistro")
	public String addServicioEstetico(Model model) {
		model.addAttribute(viewServicioEstetico, new ServicioEstetico());

		return "crearServicioEstetico";
	}

	@PostMapping("/crearServicioEstetico")
	public String addServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return viewError;
		}
		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
			return viewError;
		}
		return redirect;
	}

	@PostMapping("/servicioEsteticoUpdate/{id}")
	public String updateServicioEsteticoService(@PathVariable("id") Integer id,
			@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return viewError;
		}

		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
			return viewError;
		}
		String message1 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message1);
		return redirect;
	}

	@GetMapping("/servicioEsteticoDelete/{id}")
	public String deleteServicioEsteticoService(@PathVariable("id") Integer id, Model model) {
		try {
			service.deleteServicioEsteticoById(id);
		} catch (Exception e) {
			return viewError;
		}
		String message2 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message2);
		return redirect;
	}

		@GetMapping("/")
		public String listado(Model model) {
			List<ServicioEstetico> servicios;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			String provincia = "";
			String anonymousUser1 = "anonymousUser";
			if(!username.equals(anonymousUser1) && !username.equals(admin)) {
				Usuario u = this.usuarioService.findByUsuario(username);
				provincia = u.getProvincia();
			}
			try {
				if(!username.equals(anonymousUser1) && !username.equals(admin)) {
					servicios = service.getAllServicioEsteticosPorProvincia(provincia);
					model.addAttribute(listaServicios, servicios);
				} else {
					servicios = service.getAllServicioEsteticos();
					model.addAttribute(listaServicios, servicios);
				}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
		}

		@GetMapping("/mascotas")
		public String listadoMascotas(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin));
			try {
				if (hasAdminRole) {

					serviciosTipo = service.getAllServicioEsteticosPorTipo("Mascotas");
					model.addAttribute(listaServicios, serviciosTipo);

				} else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Mascotas");
					model.addAttribute(listaServicios, serviciosTipo);
				}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
		}
		
		@GetMapping("/tinte")
		public String listadoTinte(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin));
			try {
				if(hasAdminRole) {				
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Tinte");
					model.addAttribute(listaServicios, serviciosTipo);
					}else {
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Tinte");
				model.addAttribute(listaServicios, serviciosTipo);
			}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
		}
		
		@GetMapping("/pedicura-y-manicura")
		public String listadoPedicuraYManicura(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin));
			try {
				if(hasAdminRole) {	
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Pedicura y Manicura");
					model.addAttribute(listaServicios, serviciosTipo);									
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Pedicura y Manicura");
					model.addAttribute(listaServicios, serviciosTipo);
				}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
		}
				
		@GetMapping("/depilacion")
		public String listadoDepilacion(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin));
			try {
				if(hasAdminRole) {			
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Depilacion");
					model.addAttribute(listaServicios, serviciosTipo);										
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Depilacion");
					model.addAttribute(listaServicios, serviciosTipo);
				}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
		}
				
		@GetMapping("/peluqueria")
		public String listadoPeluqueria(Model model) {
			List<ServicioEstetico> serviciosTipo;
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin));
			try {
				if(hasAdminRole) {				
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Peluqueria");
					model.addAttribute(listaServicios, serviciosTipo);										
				}else {
					serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Peluqueria");
					model.addAttribute(listaServicios, serviciosTipo);
				}
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return viewListado;
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
				model.addAttribute(viewServicioEstetico, servicioEstetico);
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
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
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return redirect;
		}

		@GetMapping("/ServicioEditar/{idServ}")
		public String servicioEditar(@PathVariable("idServ") long id, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (username.equals(admin)) {
				ServicioEstetico servicioEstetico = new ServicioEstetico();
				try {
					servicioEstetico = service.getServicioEsteticoById((int) id);
					model.addAttribute(viewServicioEstetico, servicioEstetico);
					return "editarServicio";
				} catch (Exception e) {
					logger.log(Logger.Level.FATAL, e.getMessage());
					return viewError;
				}
			} else {
				return viewError;
			}
		}
	
		@PostMapping("/editarServicioEstetico")
		public String editarServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result,
				Model model) {
			if (result.hasErrors()) {
				return viewError;
			}
			try {
				service.createOrUpdateServicioEstetico(servicioEstetico);
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return redirect;
		}
	
		@GetMapping("/ServicioBorrar/{idServ}")
		public String servicioBorrar(@PathVariable("idServ") Integer id, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (username.equals(admin)) {
				ServicioEstetico servicioEstetico = new ServicioEstetico();
				try {
					service.deleteServicioEsteticoById(id);
					model.addAttribute(viewServicioEstetico, servicioEstetico);
					return redirect;
				} catch (Exception e) {
					logger.log(Logger.Level.FATAL, e.getMessage());
					return viewError;
				}
			} else {
				return viewError;
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
			} catch (Exception e) {
				logger.log(Logger.Level.FATAL, e.getMessage());
				return viewError;
			}
			return redirect;
		}
}
