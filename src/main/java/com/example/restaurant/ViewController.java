package com.example.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
    @GetMapping("/signup")
    public String signup(){
        return "signup";// vị trí file html (tính từ sau /templates, lược bỏ html -> ok)
    }
    @GetMapping("/login")
    public String signin(){
        return "login";// vị trí file html (tính từ sau /templates)
    }
    @GetMapping("/home")
    public String index(){
        return "index";
    }
    @GetMapping("/profile")
    public String profile(){
        return "/user/myprofile";
    }
    @GetMapping("/restaurant/{id}")
    public String restView(){
        return "/restaurant/info";
    }

}
