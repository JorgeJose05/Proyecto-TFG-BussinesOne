package com.BussinesOne.demo.controllers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controlador {
 @GetMapping("/")
 public String index(Model model){
 model.addAttribute("hora", LocalDateTime.now());
 return "index";
 }
}
