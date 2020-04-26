package app.fastyleApplication.fastyle.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

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

	@GetMapping("/servicioEsteticoRegistro")
	public String addServicioEstetico(Model model) {
		String servicioEstetico1 = "servicioEstetico"; 
		model.addAttribute(servicioEstetico1, new ServicioEstetico());

		return "crearServicioEstetico";
	}

	@PostMapping("/crearServicioEstetico")
	public String addServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		String viewError1 = "error";
		String redirect1 = "redirect:/";
		if (result.hasErrors()) {
			return viewError1;
		}
		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError1;
		}
		return redirect1;
	}

//	@GetMapping("/servicioEsteticoEdit/{id}")
//	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//		String viewError2 = "error";
//		String redirect2 = "redirect:/";
//		try {
//			ServicioEstetico servicioEstetico = service.getServicioEsteticoById(id);
//		} catch (Exception e) {
//			return viewError2;
//		}
//		model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
//		return redirect2;
//	}

	@PostMapping("/servicioEsteticoUpdate/{id}")
	public String updateServicioEsteticoService(@PathVariable("id") Integer id,
			@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		String viewError3 = "error";
		String redirect3 = "redirect:/";
		if (result.hasErrors()) {
			return viewError3;
		}

		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError3;
		}
		String message1 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message1);
		return redirect3;
	}

	@GetMapping("/servicioEsteticoDelete/{id}")
	public String deleteServicioEsteticoService(@PathVariable("id") Integer id, Model model) {
		String viewError4 = "error";
		String redirect4 = "redirect:/";
		try {
			service.deleteServicioEsteticoById(id);
		} catch (Exception e) {
			return viewError4;
		}
		String message2 = "Añadir lo que se necesite en la vista a la que se va redirigir";
		model.addAttribute("message", message2);
		return redirect4;
	}

	//@GetMapping("/listadoServicios")
		@GetMapping("/")
		public String listado(Model model) {
			List<ServicioEstetico> servicios = new ArrayList<ServicioEstetico>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			String provincia = "";
			String viewError5 = "error";
			String anonymousUser1 = "anonymousUser";
			String admin1 = "admin";
			String listaServicios1 = "listaServicios";
			String viewListado1 = "listadoServicios";
			if(!username.equals(anonymousUser1) && !username.equals(admin1)) {
				Usuario u = this.usuarioService.findByUsuario(username);
				provincia = u.getProvincia();
			}
			try {
				if(!username.equals(anonymousUser1) && !username.equals(admin1)) {
					servicios = service.getAllServicioEsteticosPorProvincia(provincia);
					model.addAttribute(listaServicios1, servicios);
				} else {
					servicios = service.getAllServicioEsteticos();
					model.addAttribute(listaServicios1, servicios);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return viewError5;
			}
			return viewListado1; // view
		}
		
		
		// Para la categoria mascotas
				@GetMapping("/mascotas")
				public String listadoMascotas(Model model) {
					List<ServicioEstetico> serviciosTipo = new ArrayList<ServicioEstetico>();
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					Usuario u = this.usuarioService.findByUsuario(username);
					String provincia = u.getProvincia();
					String viewError6 = "error";
					String listaServicios2 = "listaServicios";
					String viewListado2 = "listadoServicios";
					String rolAdmin1 = "ROLE_ADMIN";
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					boolean hasAdminRole = authentication.getAuthorities().stream()
							.anyMatch(r -> r.getAuthority().equals(rolAdmin1));
					try {
						if(hasAdminRole ) {
							
							serviciosTipo= service.getAllServicioEsteticosPorTipo("Mascotas");
							model.addAttribute(listaServicios2, serviciosTipo);
							
							
							}else { // para clientes y esteticistas
						serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Mascotas");
						model.addAttribute(listaServicios2, serviciosTipo);
					}
					} catch (Exception e) {
						e.printStackTrace();
						return viewError6;
					}
					return viewListado2; // view
				}
		
		
		// @GetMapping("/listadoServicios/tinte")
		@GetMapping("/tinte")
		public String listadoTinte(Model model) {
			List<ServicioEstetico> serviciosTipo = new ArrayList<ServicioEstetico>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			String viewError7 = "error";
			String listaServicios3 = "listaServicios";
			String viewListado3 = "listadoServicios";
			String rolAdmin2 = "ROLE_ADMIN";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin2));
			
			try {
				if(hasAdminRole) {
					
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Tinte");
					model.addAttribute(listaServicios3, serviciosTipo);
					
					
					}else { // para clientes y esteticistas
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Tinte");
				model.addAttribute(listaServicios3, serviciosTipo);
			}
			} catch (Exception e) {
				e.printStackTrace();
				return viewError7;
			}
			return viewListado3; // view
		}
		
		// @GetMapping("/listadoServicios/pedicura-y-manicura")
		@GetMapping("/pedicura-y-manicura")
		public String listadoPedicuraYManicura(Model model) {
			List<ServicioEstetico> serviciosTipo = new ArrayList<ServicioEstetico>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			String viewError8 = "error";
			String listaServicios4 = "listaServicios";
			String viewListado4 = "listadoServicios";
			String rolAdmin3 = "ROLE_ADMIN";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin3));
			try {
				if(hasAdminRole) {
					
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Pedicura y Manicura");
					model.addAttribute(listaServicios4, serviciosTipo);
					
					
					}else {
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Pedicura y Manicura");
				model.addAttribute(listaServicios4, serviciosTipo);
					}
			} catch (Exception e) {
				e.printStackTrace();
				return viewError8;
			}
			return viewListado4; // view
		}
				
		// @GetMapping("/listadoServicios/depilacion")
		@GetMapping("/depilacion")
		public String listadoDepilacion(Model model) {
			List<ServicioEstetico> serviciosTipo = new ArrayList<ServicioEstetico>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			String viewError9 = "error";
			String listaServicios5 = "listaServicios";
			String viewListado5 = "listadoServicios";
			String rolAdmin4 = "ROLE_ADMIN";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin4));
			try {
				if(hasAdminRole) {
					
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Depilacion");
					model.addAttribute(listaServicios5, serviciosTipo);
					
					
					}else {
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Depilacion");
				model.addAttribute(listaServicios5, serviciosTipo);
					}
			} catch (Exception e) {
				e.printStackTrace();
				return viewError9;
			}
			return viewListado5; // view
		}
				
		// @GetMapping("/listadoServicios/peluqueria")
		@GetMapping("/peluqueria")
		public String listadoPeluqueria(Model model) {
			List<ServicioEstetico> serviciosTipo = new ArrayList<ServicioEstetico>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			String provincia = u.getProvincia();
			String viewError10 = "error";
			String listaServicios6 = "listaServicios";
			String viewListado6 = "listadoServicios";
			String rolAdmin5 = "ROLE_ADMIN";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			boolean hasAdminRole = authentication.getAuthorities().stream()
					.anyMatch(r -> r.getAuthority().equals(rolAdmin5));
			try {
				if(hasAdminRole) {
					
					serviciosTipo= service.getAllServicioEsteticosPorTipo("Peluqueria");
					model.addAttribute(listaServicios6, serviciosTipo);
					
					
					}else {
				serviciosTipo = service.getAllServicioEsteticosPorProvinciaYTipo(provincia, "Peluqueria");
				model.addAttribute(listaServicios6	, serviciosTipo);}
			} catch (Exception e) {
				e.printStackTrace();
				return viewError10;
			}
			return viewListado6; // view
		}
				

	@GetMapping("/servicioInfo/{id}")
	public String diplayInfo(@PathVariable("id") long id, Model model) {
		List<Esteticista> servicios = new ArrayList<Esteticista>();
		ServicioEstetico servicioEstetico = new ServicioEstetico();
		String anonymousUser2 = "anonymousUser";
		String servicioEstetico2 = "servicioEstetico"; 
		String viewError11 = "error";
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
			model.addAttribute(servicioEstetico2, servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError11;
		}
		return "servicioInfo"; // view
	}

	@GetMapping("/ServicioUnir/{idServ}")
	public String ServicioUnir(@PathVariable("idServ") long id, Model model) {
		ServicioEstetico servicioEstetico = new ServicioEstetico();
		String viewError12 = "error";
		String redirect5 = "redirect:/";
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Esteticista c = this.esteticistaService.findByUsuario(u);
			servicioEstetico.getEsteticista().add(c);
			this.service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError12;
		}
		return redirect5;
	}
	
	@GetMapping("/ServicioEditar/{idServ}")
	public String ServicioEditar(@PathVariable("idServ") long id, Model model) {
		String servicioEstetico3 = "servicioEstetico"; 
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String viewError13 = "error";
		String admin2 = "admin";
		if(username.equals(admin2)) {
		ServicioEstetico servicioEstetico = new ServicioEstetico();
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			model.addAttribute(servicioEstetico3, servicioEstetico);
			return "editarServicio";
		} catch (Exception e) {
			e.printStackTrace();
			return viewError13;
		}
		}else {
			return viewError13;
		}
	}
	
	@PostMapping("/editarServicioEstetico")
	public String editarServicioEstetico(@Valid ServicioEstetico servicioEstetico, BindingResult result, Model model) {
		String viewError14 = "error";
		String redirect6 = "redirect:/";
		if (result.hasErrors()) {
			return viewError14;
		}
		try {
			service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError14;
		}
		return redirect6;
	}
	
	@GetMapping("/ServicioBorrar/{idServ}")
	public String ServicioBorrar(@PathVariable("idServ") Integer id, Model model) {
		String servicioEstetico4 = "servicioEstetico"; 
		String viewError15 = "error";
		String redirect7 = "redirect:/";
		String admin3 = "admin";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if(username.equals(admin3)) {
		ServicioEstetico servicioEstetico = new ServicioEstetico();
		try {
			service.deleteServicioEsteticoById(id);
			model.addAttribute(servicioEstetico4, servicioEstetico);
			return redirect7;
		} catch (Exception e) {
			e.printStackTrace();
			return viewError15;
		}
		}else {
			return viewError15;
		}
	}

	
	@GetMapping("/ServicioCancelar/{idServ}")
	public String ServicioCancelar(@PathVariable("idServ") long id, Model model) {
		ServicioEstetico servicioEstetico = new ServicioEstetico();
		String viewError16 = "error";
		String redirect8 = "redirect:/";
		try {
			servicioEstetico = service.getServicioEsteticoById((int) id);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Esteticista c = this.esteticistaService.findByUsuario(u);
			servicioEstetico.getEsteticista().remove(c);
			this.service.createOrUpdateServicioEstetico(servicioEstetico);
		} catch (Exception e) {
			e.printStackTrace();
			return viewError16;
		}
		return redirect8;
	}

}
