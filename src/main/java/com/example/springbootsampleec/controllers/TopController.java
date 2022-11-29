package com.example.springbootsampleec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springbootsampleec.forms.SignUpForm;

@Controller
public class TopController {
	
	@ResponseBody
	@GetMapping("/")
	public String index() {
	        return "こんにちは！";    
			

}
}
