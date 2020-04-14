package app.fastyleApplication.fastyle.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMapping;

import app.fastyleApplication.fastyle.CustomeFieldValidationException;
import app.fastyleApplication.fastyle.entities.Role;
import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.repository.UsuarioRepository;
import app.fastyleApplication.fastyle.util.PassGenerator;

@Controller
public class IndexController {

//	@GetMapping("/")
//    public String main(Model model) {
//        return "index"; //view
//		// return "listadoServicios";
//    }

	// Login form
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("login", true);
		return "login";
	}

	// Login form with error
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "listadoServicios";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		return "about";
	}
  
  @RequestMapping("/ayuda")
	  public String ayuda(Model model) {
	    return "ayuda";
	}

	@RequestMapping("/politicaCookies")
	public String politicaCookies(Model model) {
		return "politicaCookies";
	}

	@RequestMapping("/politicaPrivacidad")
	public String politicaPrivacidad(Model model) {
		return "politicaPrivacidad";
	}
}