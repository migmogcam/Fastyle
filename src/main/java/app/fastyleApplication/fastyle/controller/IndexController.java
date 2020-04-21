package app.fastyleapplication.fastyle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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