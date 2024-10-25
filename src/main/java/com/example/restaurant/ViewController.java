package com.example.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String index(){
        return "index";
    }
    @GetMapping("/menu")
    public String menu(){
        return "menu";
    }

    @GetMapping("/rest/menu/{menuId}")
    public String menuindex() {
        return "/rest/menu/{menuId}";
    }
}