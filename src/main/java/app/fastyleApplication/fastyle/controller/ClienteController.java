package app.fastyleApplication.fastyle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.repository.ClienteRepository;
import app.fastyleApplication.fastyle.services.ClienteService;

@Controller
public class ClienteController {
	
	
	
	
	    
//	    private final ClienteRepository clienteRepository;
//
//	    @Autowired
//	    public ClienteController(ClienteRepository clienteRepository) {
//	        this.clienteRepository = clienteRepository;
//	    }
//	    
//	    @GetMapping("/signup")
//	    public String showSignUpForm(Cliente cliente) {
//	        return "add-client";
//	    }
//	    
//	    @PostMapping("/add-client")
//	    public String addUser(@Valid Cliente cliente, BindingResult result, Model model) {
//	        if (result.hasErrors()) {
//	            return "add-client";
//	        }
//	        
//	        clienteRepository.save(cliente);
//	        model.addAttribute("users", clienteRepository.findAll());
//	        return "index";
//	    }
//	    
//	    @GetMapping("/edit/{id}")
//	    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//	        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//	        model.addAttribute("cliente", cliente);
//	        return "update-client";
//	    }
//	    
//	    @PostMapping("/update/{id}")
//	    public String updateUser(@PathVariable("id") long id, @Valid Cliente cliente, BindingResult result, Model model) {
//	        if (result.hasErrors()) {
//	            cliente.setId(id);
//	            return "update-user";
//	        }
//	        
//	        userRepository.save(user);
//	        model.addAttribute("users", userRepository.findAll());
//	        return "index";
//	    }
//	    
//	    @GetMapping("/delete/{id}")
//	    public String deleteUser(@PathVariable("id") long id, Model model) {
//	        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//	        userRepository.delete(user);
//	        model.addAttribute("users", userRepository.findAll());
//	        return "index";
//	    }
//	
	
	
	
//	@Autowired
//	ClienteService service;
//	
//	@PostMapping("/clienteRegistro")
//    public String addCliente(@Valid Cliente cliente, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//        	//TODO añadir vista errores
//            return "vista de errores";
//        } 
//        try {
//			service.createOrUpdateCliente(cliente);
//		} catch (Exception e) {
//			e.printStackTrace();
//        	//TODO añadir vista errores
//            return "vista de errores";
//		}
//        //TODO Añadir vista de creacion correcta
//        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
//        return "vista todo OK";
//    }
//	
//	@GetMapping("/clienteEdit/{id}")
//	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//	    try {
//			Cliente cliente = service.getClienteById(id);
//		} catch (Exception e) {
//        	//TODO añadir vista errores
//            return "vista de errores";
//		}
//	     
//        //TODO Añadir vista de creacion correcta
//        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
//        return "vista todo OK";
//	}
//	
//	@PostMapping("/clienteUpdate/{id}")
//	public String updateCliente(@PathVariable("id") Integer id, @Valid Cliente cliente, 
//	  BindingResult result, Model model) {
//	    if (result.hasErrors()) {
//        	//TODO añadir vista errores
//            return "vista de errores";
//	    }
//	         
//	    try {
//			service.createOrUpdateCliente(cliente);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	  //TODO Añadir vista de creacion correcta
//        model.addAttribute("Añadir lo que se necesite en la vista a la que se va redirigir");
//        return "vista todo OK";
//	}

	
	
	
	 
	    // additional CRUD methods
	

//	@GetMapping("/cliente")
//	  public String clienteForm(Model model) {
//	    model.addAttribute("cliente", new Cliente());
//	    return "cliente";
//	  }
//
//	  @PostMapping("/cliente")
//	  public String clienteSubmit(@ModelAttribute Cliente cliente) {
//	    return "resultCliente";
//	  }


	}

