package com.example.chamcong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/index")
    public String index() {
        return "index"; // => templates/index.html
    }

    @GetMapping("/")
    public String loginPage() {
        return "login"; // => templates/login.html
    }

    @GetMapping("/chamcong")
    public String chamCongPage() {
        return "chamcong"; // => templates/chamcong.html
    }

    @GetMapping("/dangkitaikhoan")
    public String dangKyTaiKhoanPage() {
        return "dangkitaikhoan"; // => templates/dangkitaikhoan.html
    }

    @GetMapping("/chamcongnhanvien")
    public String chamCongnhanvienPage() {
        return "chamcongnhanvien"; // => templates/chamcongnhanvien.html
    }

    @GetMapping("/congviec")
    public String congvienPage() {
        return "congviec";
    }

    @GetMapping("/nhanvien")
    public String nhanvienPage() {
        return "nhanvien";
    }

    @GetMapping("/user")
    public String CanhanPage() {
        return "user";
    }

}
