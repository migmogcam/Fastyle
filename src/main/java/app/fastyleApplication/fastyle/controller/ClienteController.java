package app.fastyleapplication.fastyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.fastyleapplication.fastyle.services.ClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	

}
