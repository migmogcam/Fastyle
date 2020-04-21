package app.fastyleApplication.fastyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.fastyleApplication.fastyle.services.EsteticistaService;

@Controller
public class EsteticistaController {
	
	@Autowired
	EsteticistaService service;
	

}
