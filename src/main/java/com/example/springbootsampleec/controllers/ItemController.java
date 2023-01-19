package com.example.springbootsampleec.controllers;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.forms.ItemCreateForm;
import com.example.springbootsampleec.forms.ItemEditForm;
import com.example.springbootsampleec.forms.ItemSearchForm;
import com.example.springbootsampleec.services.ItemService;

@RequestMapping("/items")
@Controller
public class ItemController { 
    private final ItemService itemService;
    
    public ItemController(
        ItemService itemService
    ) {
        this.itemService = itemService;
        
    }
    
    @PersistenceContext
    EntityManager entityManager;
    
    @GetMapping("/")    
    public String index(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("formModel") Item item,
//        @PathVariable(name = "")	String name,
//  		@PathVariable(name = "")	String description,
//        @RequestParam(defaultValue = "1")	Long id,
        Model model
    ) {
        List<Item> items = itemService.findAll();
//        Optional<Item> items = itemService.findById(id);
        model.addAttribute("msg", "検索");
        model.addAttribute("user", user);
        model.addAttribute("items", items);
//        model.addAttribute("items", items);
//        model.addAttribute("name", name);
//      	model.addAttribute("description", description);
        model.addAttribute("title", "商品一覧");
        model.addAttribute("main", "items/index::main");
        return "layout/logged_in";    
    }
    
//    @RequestMapping("/data")
//  public String data(
//		  
//  		@AuthenticationPrincipal(expression = "user") User user,
//  		@ModelAttribute("formModel") Item item,
//  		BindingResult bindingResult,
//  		@RequestParam(defaultValue = "")	String name,
//  		@RequestParam(defaultValue = "")	String description,
//  		Model model
//  		){
//  	if(bindingResult.hasErrors()){
//          return name;
//      }
//  	model.addAttribute("msg", "検索");
//  	//itemのゲッターで各値を取得する
//  	List<Item> items = ItemService.search(
//  			item.getName(),
//  			item.getDescription()
//  			);
//  	model.addAttribute("formModel",item);
//  	model.addAttribute("user", user);
//  	model.addAttribute("title", "商品一覧");
////  	model.addAttribute("item", items);
//  	model.addAttribute("name", name);
//  	model.addAttribute("description", description);
//  	 model.addAttribute("main", "items/index::main");
//  	return "layout/logged_in";
//  	
//  }
   
    
    @GetMapping("/data")//検索
    public String data(
    		@AuthenticationPrincipal(expression = "user") User user,
    		
//    		@Valid ItemSearchForm itemSearchForm,
    		 @RequestParam(defaultValue = "")  String name,
    		 @RequestParam(defaultValue = "")  String description,
//    		 BindingResult bindingResult,
    		Model model
    		
    		){
    	
//    	List<Item> items = itemService.findAll();
//      	Optional<Item> items = itemService.findAllByNameContaining(name);
//      	List<Item> items_name = itemService.findAllByNameContaining(name);
//      	List<Item> items_description = itemService.findAllByDescriptionContaining(description);
      	List<Item> search_result = itemService.search(name, description);
      	model.addAttribute("user", user);
      	model.addAttribute("items", search_result); //エラーはいたら items_name に差し替え
//      	model.addAttribute("items1", items_description);
    	model.addAttribute("name", name);
    	model.addAttribute("description", description);
    	model.addAttribute("msg", "検索");
    	model.addAttribute("title", "キーの取得");
    	model.addAttribute("main", "items/index::main");
    	return "layout/logged_in";
    	
    }
    
  //検索
//    @GetMapping("/search")
//    public String search(
//    		@AuthenticationPrincipal(expression = "user") User user,
//    		@PathVariable("name") String name,
////            @RequestParam(defaultValue = "")  String name,
//            Model model
//    		) {
////    	List<Item> items = itemService.findAll();
////    	 Optional<List<Item>> items = itemService.findByNameContaining(name);
////    	 Optional<Item> items = itemService.findAllByNameContaining(name);
//    	List<Item> items = itemService.findAllByNameContaining(name,description);
//         model.addAttribute("user", user);
//         model.addAttribute("item", items);
//         model.addAttribute("name", name);
//         model.addAttribute("title", "kennsaku");
//         model.addAttribute("main", "items/index::main");
//         return "layout/logged_in";
//    }
    @GetMapping("/top")    
    public String top(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
        List<Item> items = itemService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("title", "TOP");
        model.addAttribute("main", "items/top::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/create")    
    public String create(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemCreateForm") ItemCreateForm itemCreateForm,
        Model model
    ) {
        model.addAttribute("title", "商品の新規作成");
        model.addAttribute("user", user);
        model.addAttribute("main", "items/create::main");
        return "layout/logged_in";    
    }
    
    @PostMapping("/create")    
    public String createProcess(
        @AuthenticationPrincipal(expression = "user") User user,
        @Valid ItemCreateForm itemCreateForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model
        ) {
        if(bindingResult.hasErrors()){
            return create(user, itemCreateForm, model);
        }
        itemService.register(
            itemCreateForm.getName(),
            itemCreateForm.getPrice(),
            itemCreateForm.getStock(),
            itemCreateForm.getDescription(),
            itemCreateForm.getImage()
        );
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品を追加しました");
        return "redirect:/admin";
    }
 
    @GetMapping("/detail/{id}")    
    public String detail(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Long id,
        Model model
    ) {
        Item item = itemService.findById(id).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "商品の詳細");
        model.addAttribute("main", "items/detail::main");
        return "layout/logged_in";    
    }
    
    
    
    //検索機能
//    @PostMapping("search/{name}")
//    public String searchName(
//    		@AuthenticationPrincipal(expression = "user") User user,
//    		@PathVariable("name")  String name,
//    		Model model
//    		) {
//    	Optional<Item> items = itemService.findBynameLike(name);
//    	model.addAttribute("items", items);
//    	model.addAttribute("user", user);
//        model.addAttribute("title", "検索結果");
//        model.addAttribute("main", "items/search::main");
//    	return "layout/logged_in";
//    }
 
    @GetMapping("/edit/{id}")    
    public String edit(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemEditForm") ItemEditForm itemEditForm,
        @PathVariable("id")  Integer id,
        Model model) {
        Item item = itemService.findById(id).orElseThrow();
        itemEditForm.setName(item.getName());
        itemEditForm.setPrice(item.getPrice());
        itemEditForm.setStock(item.getStock());
        itemEditForm.setDescription(item.getDescription());
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "投稿の編集");
        model.addAttribute("main", "items/edit::main");
        return "layout/logged_in";    
    }
    
    @PostMapping("/update/{id}")    
    public String update(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Integer id,
        @Valid ItemEditForm itemEditForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model) {
        if(bindingResult.hasErrors()){
            return edit(user, itemEditForm, id, model);
        }
        itemService.updateItem(
            id,
            itemEditForm.getName(),
            itemEditForm.getPrice(),
            itemEditForm.getStock(),
            itemEditForm.getDescription()
        );  
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品情報の更新が完了しました");
        return "redirect:/admin";
    }
    
    @PostMapping("/delete/{id}")    
    public String delete(
        @PathVariable("id")  Integer id,
        RedirectAttributes redirectAttributes,
        Model model) {
        itemService.delete(id);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品の削除が完了しました");
        return "redirect:/admin";  
    }
    
    @PostMapping("/addcart/{id}")
    public String addcart(
    		@PathVariable("id")  Integer id,
    		@AuthenticationPrincipal(expression = "user") User user,
            Model model) {
//    	List<Item> items = itemService.findAllById(id);
//    	List<Item> items = itemService.findAll();
//        Item item = itemService.findById(id).orElseThrow();
        itemService.getOrderItems(
        		user,
        		id
        		);
        return "redirect:/items/";
//        model.addAttribute("item", item);
//        model.addAttribute("item", item);
//        model.addAttribute("user", user);
//        model.addAttribute("name", "name");
//        model.addAttribute("title", "カートに追加しました");
//        model.addAttribute("main", "items/cart::main");
//        return "layout/logged_in";    
    }
   
}