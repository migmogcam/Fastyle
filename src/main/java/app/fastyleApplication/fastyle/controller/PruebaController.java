package app.fastyleApplication.fastyle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Esteticista;


@Controller
public class PruebaController {

	@GetMapping("/registroEsteticista")
	public String main(Model model) {
		return "registroEsteticista"; // view
	}
	
	@PostMapping("/registroEsteticista")
	public String registroEsteticistaSubmit(@ModelAttribute Esteticista esteticista) {
		return "result";
	}

}
