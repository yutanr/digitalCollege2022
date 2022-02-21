package com.example.springbootsampleec.controllers;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.springbootsampleec.entities.User;
 
@RequestMapping("/items")
@Controller
public class ItemController { 
    @GetMapping("/")    
    public String index(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("title", "商品一覧");
        model.addAttribute("main", "items/index::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/create")    
    public String create(Model model) {
        model.addAttribute("title", "商品の新規作成");
        model.addAttribute("main", "items/create::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/detail/{id}")    
    public String detail(Model model) {
        model.addAttribute("title", "商品の詳細");
        model.addAttribute("main", "items/detail::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/edit/{id}")    
    public String edit(Model model) {
        model.addAttribute("title", "投稿の編集");
        model.addAttribute("main", "items/edit::main");
        return "layout/logged_in";    
    }
}