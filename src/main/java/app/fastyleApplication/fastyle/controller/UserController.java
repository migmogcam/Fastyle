package app.fastyleApplication.fastyle.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.fastyleApplication.fastyle.util.PassGenerator;

@Controller
public class UserController {

	private final String TAB_FORM = "formTab";
	private final String TAB_LIST = "listTab";

	@Autowired
	ClienteService userService;
	
	@Autowired
	EsteticistaService esteticistaService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthorityService autoRepository;

//	@GetMapping({"/","/login"})
//	public String index() {
//		return "index";
//	}

	@GetMapping("/signup")
	public String signup(Model model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);

		model.addAttribute("signup", true);
		model.addAttribute("userForm", new UserDTO());
		model.addAttribute("roles", roles);
		return "user-signup";
	}

	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm") UserDTO user, BindingResult result, ModelMap model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute("userForm", user);
		model.addAttribute("roles", roles);
		model.addAttribute("signup", true);
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
		us.setEMail(user.getEMail());
		us.setName(user.getName());
		us.setPassword(pass);
		us.setProvincia(user.getProvincia());
		us.setUsuario(user.getUsuario());
		Cliente cliente = new Cliente();
		Esteticista esteticista = new Esteticista();
		if (user.getAutority().equals("ROLE_CLIENTE")) {

			cliente.setCitas(citas);
			cliente.setUsuario(us);
		} else if (user.getAutority().equals("ROLE_ESTETICISTA")) {

			esteticista.setCitas(citas);
			esteticista.setDescripcion(user.getDescripcion());
			esteticista.setUsuario(us);
		}

		if (result.hasErrors()) {
			return "user-signup";
		} else {
			try {
				if (user.getAutority().equals("ROLE_CLIENTE")) {
					userService.createOrUpdateCliente(cliente);
				} else if (user.getAutority().equals("ROLE_ESTETICISTA")) {
					esteticistaService.createOrUpdateCliente(esteticista);
				}

			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
			}
		}
		return "accionRealizada";
	}

//	private void baseAttributerForUserForm(Model model, User user,String activeTab) {
//		model.addAttribute("userForm", user);
//		model.addAttribute("userList", userService.getAllUsers());
//		model.addAttribute("roles",roleRepository.findAll());
//		model.addAttribute(activeTab,"active");
//	}

//	@GetMapping("/userForm")
//	public String userForm(Model model) {
//		baseAttributerForUserForm(model, new User(), TAB_LIST );
//		return "user-view";
//	}
//	
//	@PostMapping("/userForm")
//	public String createUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, Model model) {
//		if(result.hasErrors()) {
//			baseAttributerForUserForm(model, user, TAB_FORM);
//		}else {
//			try {
//				userService.createUser(user);
//				baseAttributerForUserForm(model, new User(), TAB_LIST );
//				
//			} catch (CustomeFieldValidationException cfve) {
//				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
//				baseAttributerForUserForm(model, user, TAB_FORM );
//			}catch (Exception e) {
//				model.addAttribute("formErrorMessage",e.getMessage());
//				baseAttributerForUserForm(model, user, TAB_FORM );
//			}
//		}
//		return "user-view";
//	}

//	@GetMapping("/editUser/{id}")
//	public String getEditUserForm(Model model, @PathVariable(name ="id")Long id)throws Exception{
//		User userToEdit = userService.getUserById(id);
//
//		baseAttributerForUserForm(model, userToEdit, TAB_FORM );
//		model.addAttribute("editMode","true");
//		model.addAttribute("passwordForm",new ChangePasswordForm(id));
//		
//		return "user-form/user-view";
//	}

//	@PostMapping("/editUser")
//	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, Model model) {
//		if(result.hasErrors()) {
//			baseAttributerForUserForm(model, user, TAB_FORM );
//			model.addAttribute("editMode","true");
//			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
//		}else {
//			try {
//				userService.updateUser(user);
//				baseAttributerForUserForm(model, new User(), TAB_LIST );
//			} catch (Exception e) {
//				model.addAttribute("formErrorMessage",e.getMessage());
//				
//				baseAttributerForUserForm(model, user, TAB_FORM );
//				model.addAttribute("editMode","true");
//				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
//			}
//		}
//		return "user-form/user-view";
//		
//	}

//	@GetMapping("/userForm/cancel")
//	public String cancelEditUser(ModelMap model) {
//		return "redirect:/userForm";
//	}

//	@GetMapping("/deleteUser/{id}")
//	public String deleteUser(Model model, @PathVariable(name="id")Long id) {
//		try {
//			userService.deleteUser(id);
//		} 
//		catch (UsernameOrIdNotFound uoin) {
//			model.addAttribute("listErrorMessage",uoin.getMessage());
//		}
//		return userForm(model);
//	}

//	@PostMapping("/editUser/changePassword")
//	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
//		try {
//			if( errors.hasErrors()) {
//				String result = errors.getAllErrors()
//                        .stream().map(x -> x.getDefaultMessage())
//                        .collect(Collectors.joining(""));
//
//				throw new Exception(result);
//			}
//			userService.changePassword(form);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//		return ResponseEntity.ok("Success");
//	}

	@GetMapping({ "/loginCorrecto" })
	public String loginCorrecto() {
		return "accionRealizada";
	}
}
