package app.fastyleapplication.fastyle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.fastyleapplication.fastyle.services.EsteticistaService;

@Controller
public class EsteticistaController {
	
	@Autowired
	EsteticistaService service;
	

}
