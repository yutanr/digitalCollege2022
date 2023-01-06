package com.example.sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@ResponseBody
	@GetMapping("/top")
	public String index() {
	return "初めてのSpringBoot";
}
	@ResponseBody
    @GetMapping("/simple")    
    public String simple() {
        System.out.println("simple");
        return "シンプルなルーティング！";    
    }
}
