package com.nextlevelfit.nextlevelfit.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "NextLevelFit");
        return "home-page";
    }


}
