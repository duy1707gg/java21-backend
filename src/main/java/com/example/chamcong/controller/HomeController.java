package com.example.chamcong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/index"})
    public String index() {
        return "index"; // Trả về "index.html" trong thư mục templates
    }
}
