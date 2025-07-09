package com.org.shbvn.svbsimo.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "SVB SIMO API Testing Interface");
        return "index";
    }

    @GetMapping("/auth")
    public String auth(Model model) {
        model.addAttribute("title", "Authentication APIs");
        return "auth";
    }

    @GetMapping("/batch")
    public String batch(Model model) {
        model.addAttribute("title", "Batch Job APIs");
        return "batch";
    }

    @GetMapping("/file")
    public String file(Model model) {
        model.addAttribute("title", "File Management APIs");
        return "file";
    }
} 