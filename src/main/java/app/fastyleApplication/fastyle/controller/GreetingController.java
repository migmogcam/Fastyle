//package com.example.handlingformsubmission;
package app.fastyleApplication.fastyle.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.Greeting;

@Controller
public class GreetingController {

  @GetMapping("/greeting")
  public String greetingForm(Model model) {
    model.addAttribute("esteticista", new Esteticista());
    return "greeting";
  }

  @PostMapping("/greeting")
  public String greetingSubmit(@ModelAttribute Esteticista esteticista) {
    return "result";
  }

}