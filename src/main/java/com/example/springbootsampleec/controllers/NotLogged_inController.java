package com.example.springbootsampleec.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.services.ItemService;

@RequestMapping("/top")
@Controller
public class NotLogged_inController {
	private ItemService itemService;
	public void ItemController(
	        ItemService itemService
	    ) {
	        this.itemService = itemService;
	    }
	@GetMapping("/")    
    public String layoutSample(
    		@AuthenticationPrincipal(expression = "user") User user,
    		Model model) {
		 List<Item> items = itemService.findAll();
	        model.addAttribute("user", user);
	        model.addAttribute("items", items);
	        model.addAttribute("title", "商品一覧");
	        model.addAttribute("main", "items/index::main");
       
        return "layout/not_logged_in";    
    }
}

