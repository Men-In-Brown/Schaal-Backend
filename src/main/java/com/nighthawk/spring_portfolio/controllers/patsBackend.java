package com.nighthawk.spring_portfolio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.LinkrPAT;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.PatJpaRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class patsBackend {

    @Autowired
    PatJpaRepository patRepo;
    // @GetMapping handles GET request for /greet, maps it to greeting() method
    @GetMapping("/patView")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String pats(Model model) {
        ArrayList<LinkrPAT> pt = new ArrayList<>();
        for(LinkrPAT l : patRepo.findAll()){
            pt.add(l);
        }
        model.addAttribute("pats", pt);
        return "pat"; 
    }
}