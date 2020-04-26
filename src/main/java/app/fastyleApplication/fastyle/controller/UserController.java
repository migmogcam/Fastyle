package app.fastyleApplication.fastyle.controller;

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

import app.fastyleApplication.fastyle.CustomeFieldValidationException;
import app.fastyleApplication.fastyle.dto.UserDTO;
import app.fastyleApplication.fastyle.entities.Role;
import app.fastyleApplication.fastyle.model.Authority;
import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.RoleRepository;
import app.fastyleApplication.fastyle.services.AuthorityService;
import app.fastyleApplication.fastyle.services.ClienteService;
import app.fastyleApplication.fastyle.services.EsteticistaService;
import app.fastyleApplication.fastyle.services.UsuarioService;
import app.fastyleApplication.fastyle.util.PassGenerator;

@Controller
public class UserController {

	private final static String TAB_FORM = "formTab";
	private final static String TAB_LIST = "listTab";

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

	@GetMapping("/signup")
	public String signup(Model model) {
		String signup1 = "signup";
		String roles1 = "roles";
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);

		model.addAttribute(signup1, true);
		model.addAttribute("userForm", new UserDTO());
		model.addAttribute(roles1, roles);
		return "user-signup";
	}

	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm") UserDTO user, BindingResult result, ModelMap model) {
		String signup2 = "signup";
		String roles2 = "roles";
		String redirect1 = "redirect:/";
		String rolCliente1 = "ROLE_CLIENTE";
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute("userForm", user);
		model.addAttribute(roles2, roles);
		model.addAttribute(signup2, true);
		model.addAttribute("registro", true);
		List<Cita> citas = new ArrayList<>();
		String pass = PassGenerator.getPassEncode(user.getPassword());

		Usuario us = new Usuario();
		us.setApellido1(user.getApellido1());
		us.setApellido2(user.getApellido2());
		Authority autor = this.autoRepository.findByAuthority(user.getAutority());
		List<Authority> autoridades = new LinkedList<Authority>();
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
		if (user.getAutority().equals(rolCliente1)) {
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
		}

		if (result.hasErrors()) {
			model.addAttribute("error-registro", true);
			return "user-signup";
		} else {
			try {
				if (user.getAutority().equals(rolCliente1)) {
					userService.createOrUpdateCliente(cliente);
				} else if (user.getAutority().equals("ROLE_ESTETICISTA")) {
					esteticistaService.createOrUpdateCliente(esteticista);
				}

			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				String message1 = "formErrorMessage";
				model.addAttribute(message1, e.getMessage());
			}
		}
		return redirect1;
	}

	@GetMapping("/perfil")
	public String getPerfil(Model model) {
		String rolCliente2 = "ROLE_CLIENTE";
		String cliente1 = "cliente";
		String error1 = "error";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(rolCliente2));
		try {
			if (hasUserRole) {
				Cliente cliente = this.userService.findByUsuario(u);
				model.addAttribute(cliente1, cliente);
				return "perfilCliente";
			} else {
				Esteticista esteticista = this.esteticistaService.findByUsuario(u);
				model.addAttribute("esteticista", esteticista);
				return "perfilEsteticista";
			}

		} catch (Exception e) {
			return error1;
		}
	}

	@GetMapping("/edit")
	public String editPerfil(Model model) {
		String rolCliente3 = "ROLE_CLIENTE";
		String cliente2 = "cliente";
		String error2 = "error";
		String editCliente1 = "editCliente";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(rolCliente3));
		try {
			if (hasUserRole) {
				Cliente cliente = this.userService.findByUsuario(u);
				model.addAttribute(cliente2, cliente);
				return editCliente1;
			} else {
				Esteticista esteticista = this.esteticistaService.findByUsuario(u);
				model.addAttribute("esteticista", esteticista);
				return "editEsteticista";
			}

		} catch (Exception e) {
			return error2;
		}
	}

	@PostMapping("/editarCliente")
	public String editCliente(@Valid @ModelAttribute("cliente") Cliente user, BindingResult result, ModelMap model) {
		String signup3 = "signup";
		String roles3 = "roles";
		String redirect2 = "redirect:/";
		String cliente3 = "cliente";
		String editCliente2 = "editCliente";
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute(cliente3, user);
		model.addAttribute(roles3, roles);
		model.addAttribute(signup3, true);

		if (user.getUsuario().getPassword() != null && !"".equals(user.getUsuario().getPassword())) {
			String pass = PassGenerator.getPassEncode(user.getUsuario().getPassword());
			user.getUsuario().setPassword(pass);
		}
		if (result.hasErrors()) {
			return editCliente2;
		} else {
			try {
				userService.createOrUpdateCliente(user);
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				String message2 = "formErrorMessage";
				model.addAttribute(message2, e.getMessage());
			}
		}
		return redirect2;
	}

	@PostMapping("/editarEsteticista")
	public String editEsteticista(@Valid @ModelAttribute("esteticista") Esteticista user, BindingResult result, ModelMap model) {
		String signup4 = "signup";
		String roles4 = "roles";
		String redirect3 = "redirect:/";
		String cliente4 = "cliente";
		String editCliente3 = "editCliente";
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute(cliente4, user);
		model.addAttribute(roles4, roles);
		model.addAttribute(signup4, true);

		if (user.getUsuario().getPassword() != null && !"".equals(user.getUsuario().getPassword())) {
			String pass = PassGenerator.getPassEncode(user.getUsuario().getPassword());
			user.getUsuario().setPassword(pass);
		}
		if (result.hasErrors()) {
			return editCliente3;
		} else {
			try {
				esteticistaService.createOrUpdateCliente(user);
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				String message3 = "formErrorMessage";
				model.addAttribute(message3, e.getMessage());
			}
		}
		return redirect3;
	}
	
	@GetMapping("/eliminar")
	public String eliminar( ModelMap model) {
		String rolCliente4 = "ROLE_CLIENTE";
		String error3 = "error";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean hasUserRole = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals(rolCliente4));
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
			return error3;
		}
	}
	
	@GetMapping({ "/loginCorrecto" })
	public String loginCorrecto(Model model) {
		String redirect4 = "redirect:/";
		model.addAttribute("loginCorrecto", true);
		return redirect4;
	}
}
