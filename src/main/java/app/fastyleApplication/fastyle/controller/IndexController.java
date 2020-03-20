package app.fastyleApplication.fastyle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
    public String main(Model model) {
        return "index"; //view
    }
	
	
	 @GetMapping("/login")
	    public String login(Model model) {
	        return "login";
	    }

}