package app.fastyleApplication.fastyle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prueba2Controller {

	@GetMapping("/registroCliente")
	public String main(Model model) {
		return "registroCliente"; // view
	}

}
