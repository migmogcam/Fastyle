package app.fastyleapplication.fastyle.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleapplication.fastyle.CustomeFieldValidationException;
import app.fastyleapplication.fastyle.dto.UserDTO;
import app.fastyleapplication.fastyle.entities.Role;
import app.fastyleapplication.fastyle.model.Authority;
import app.fastyleapplication.fastyle.model.Cita;
import app.fastyleapplication.fastyle.model.Cliente;
import app.fastyleapplication.fastyle.model.Esteticista;
import app.fastyleapplication.fastyle.model.Usuario;
import app.fastyleapplication.fastyle.repository.RoleRepository;
import app.fastyleapplication.fastyle.services.AuthorityService;
import app.fastyleapplication.fastyle.services.ClienteService;
import app.fastyleapplication.fastyle.services.EsteticistaService;
import app.fastyleapplication.fastyle.services.UsuarioService;
import app.fastyleapplication.fastyle.util.PassGenerator;

@Controller
public class UserController {

	@Autowired
	ClienteService userService;

	@Autowired
	EsteticistaService esteticistaService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthorityService autoRepository;

	@Autowired
	UsuarioService usuarioService;
	
	private static final String SIGN_UP = "signup";
	private static final String VAR_ROLES = "roles";
	private static final String VIEW_REDIRECT = "redirect:/";
	private static final String ROL_CLIENTE = "ROLE_CLIENTE";
	private static final String VAR_MESSAGE = "formErrorMessage";
	private static final String VAR_CLIENTE = "cliente";
	private static final String VIEW_ERROR = "error";
	private static final String EDIT_CLIENTE = "editCliente";

	@GetMapping("/signup")
	public String signup(Model model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);

		model.addAttribute(SIGN_UP, true);
		model.addAttribute("userForm", new UserDTO());
		model.addAttribute(VAR_ROLES, roles);
		return "user-signup";
	}

	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm") UserDTO user, BindingResult result, ModelMap model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute("userForm", user);
		model.addAttribute(VAR_ROLES, roles);
		model.addAttribute(SIGN_UP, true);
		model.addAttribute("registro", true);
		List<Cita> citas = new ArrayList<>();
		String pass = PassGenerator.getPassEncode(user.getPassword());

		Usuario us = new Usuario();
		us.setApellido1(user.getApellido1());
		us.setApellido2(user.getApellido2());
		Authority autor = this.autoRepository.findByAuthority(user.getAutority());
		List<Authority> autoridades = new LinkedList<>();
		autoridades.add(autor);
		us.setAuthorities(autoridades);
		us.setCiudad(user.getCiudad());
		us.setDireccion(user.getDireccion());
		us.setEdad(user.getEdad());
		us.setEMail(user.getEMail());
		us.setName(user.getName());
		us.setPassword(pass);
		us.setProvincia(user.getProvincia());
		us.setUsuario(user.getUsuario());
		Cliente cliente = new Cliente();
		Esteticista esteticista = new Esteticista();
		if (user.getAutority().equals(ROL_CLIENTE)) {
			cliente.setPuntos(0.0);
			cliente.setCitas(citas);
			cliente.setUsuario(us);
		} else if (user.getAutority().equals("ROLE_ESTETICISTA")) {
			List<String> imagenes = new LinkedList<>();
			imagenes.add(user.getImagen1());
			imagenes.add(user.getImagen2());
			imagenes.add(user.getImagen3());
			imagenes.add(user.getImagen4());
			esteticista.setImagenes(imagenes);
			esteticista.setCitas(citas);
			esteticista.setDescripcion(user.getDescripcion());
			esteticista.setUsuario(us);
			esteticista.setNegativo(0);
			esteticista.setPositivo(0);
		}

		if (result.hasErrors()) {
			model.addAttribute("error-registro", true);
			return "user-signup";
		} else {
			try {
				if (user.getAutority().equals(ROL_CLIENTE)) {
					userService.createOrUpdateCliente(cliente);
				} else if (user.getAutority().equals("ROLE_ESTETICISTA")) {
					esteticistaService.createOrUpdateCliente(esteticista);
				}

			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				model.addAttribute(VAR_MESSAGE, e.getMessage());
			}
		}
		return VIEW_REDIRECT;
	}

	@GetMapping("/perfil")
	public String getPerfil(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));
		try {
			if (hasUserRole) {
				Cliente cliente = this.userService.findByUsuario(u);
				model.addAttribute(VAR_CLIENTE, cliente);
				return "perfilCliente";
			} else {
				Esteticista esteticista = this.esteticistaService.findByUsuario(u);
				model.addAttribute("esteticista", esteticista);
				return "perfilEsteticista";
			}

		} catch (Exception e) {
			return VIEW_ERROR;
		}
	}

	@GetMapping("/edit")
	public String editPerfil(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));
		try {
			if (hasUserRole) {
				Cliente cliente = this.userService.findByUsuario(u);
				model.addAttribute(VAR_CLIENTE, cliente);
				return EDIT_CLIENTE;
			} else {
				Esteticista esteticista = this.esteticistaService.findByUsuario(u);
				model.addAttribute("esteticista", esteticista);
				return "editEsteticista";
			}

		} catch (Exception e) {
			return VIEW_ERROR;
		}
	}

	@PostMapping("/editarCliente")
	public String editCliente(@Valid @ModelAttribute("cliente") Cliente user, BindingResult result, ModelMap model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute(VAR_CLIENTE, user);
		model.addAttribute(VAR_ROLES, roles);
		model.addAttribute(SIGN_UP, true);

		if (user.getUsuario().getPassword() != null && !"".equals(user.getUsuario().getPassword())) {
			String pass = PassGenerator.getPassEncode(user.getUsuario().getPassword());
			user.getUsuario().setPassword(pass);
		}
		if (result.hasErrors()) {
			return EDIT_CLIENTE;
		} else {
			try {
				userService.createOrUpdateCliente(user);
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				model.addAttribute(VAR_MESSAGE, e.getMessage());
			}
		}
		return VIEW_REDIRECT;
	}

	@PostMapping("/editarEsteticista")
	public String editEsteticista(@Valid @ModelAttribute("esteticista") Esteticista user, BindingResult result, ModelMap model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute(VAR_CLIENTE, user);
		model.addAttribute(VAR_ROLES, roles);
		model.addAttribute(SIGN_UP, true);

		if (user.getUsuario().getPassword() != null && !"".equals(user.getUsuario().getPassword())) {
			String pass = PassGenerator.getPassEncode(user.getUsuario().getPassword());
			user.getUsuario().setPassword(pass);
		}
		if (result.hasErrors()) {
			return EDIT_CLIENTE;
		} else {
			try {
				esteticistaService.createOrUpdateCliente(user);
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				model.addAttribute(VAR_MESSAGE, e.getMessage());
			}
		}
		return VIEW_REDIRECT;
	}
	
	@GetMapping("/eliminar")
	public String eliminar( ModelMap model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(ROL_CLIENTE));
		try {
			if (hasUserRole) {
				Cliente client = this.userService.findByUsuario(u);
				this.userService.deleteClienteById(client.getId());
				
				return "redirect:/logout";
			} else {
				Esteticista esteticista = this.esteticistaService.findByUsuario(u);
				this.esteticistaService.deleteEsteticistaById(esteticista.getId());
				
				return "redirect:/logout";
			}

		} catch (Exception e) {
			return VIEW_ERROR;
		}
	}
	
	@GetMapping({ "/loginCorrecto" })
	public String loginCorrecto(Model model) {
		model.addAttribute("loginCorrecto", true);
		return VIEW_REDIRECT;
	}
}
