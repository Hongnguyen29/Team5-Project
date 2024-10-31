package com.example.restaurant;

import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view")
public class ViewController {
    @GetMapping("signup")
    public String signup(){
        return "signup";// vị trí file html (tính từ sau /templates, lược bỏ html -> ok)
    }
    @GetMapping("login")
    public String login(){
        return "login.html";// vị trí file html (tính từ sau /templates)
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("menus", "test");
        return "index";
    }
    @GetMapping("/menu")
    public String menu(){
        return "menu";
    }

    @GetMapping("/rest/menu/{menuId}")
    public String menuIndex() {
        return "/menu/menu";
    }
}