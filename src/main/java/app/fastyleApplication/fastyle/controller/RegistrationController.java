package app.fastyleApplication.fastyle.controller;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import app.fastyleApplication.fastyle.UserDto;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.services.UsuarioService;

public class RegistrationController {

	
	private UsuarioService service;
	
	
	@GetMapping("/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
	    Usuario userDto = new Usuario();
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
	@PostMapping("/user/registration")
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") @Valid UserDto accountDto, 
	  BindingResult result, 
	  WebRequest request, 
	  Errors errors) {
	     
	    Usuario registered = new Usuario();
	    if (!result.hasErrors()) {
	        registered = createUserAccount(accountDto, result);
	    }
	    if (registered == null) {
	        result.rejectValue("email", "message.regError");
	    }
	    if (result.hasErrors()) {
	        return new ModelAndView("registration", "user", accountDto);
	    } 
	    else {
	        return new ModelAndView("successRegister", "user", accountDto);
	    }
	}
	private Usuario createUserAccount(UserDto accountDto, BindingResult result) {
	    Usuario registered = null;
	    
	        
			registered = service.registerNewUserAccount(accountDto);
	    
	    return registered;
	}
}
