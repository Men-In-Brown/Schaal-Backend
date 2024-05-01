package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc/personRole")
public class PersonRoleViewController {

    @Autowired
    private PersonRoleJpaRepository repository;

    @GetMapping("/read")
    public String personRole(Model model) {
        List<PersonRole> list = repository.findAll();
        model.addAttribute("list", list);
        return "personRole/read";
    }

    @GetMapping("/create")
    public String personRoleAdd(PersonRole personRole) {
        return "personRole/create";
    }

    @PostMapping("/create")
    public String personRoleSave(@ModelAttribute("personRole") PersonRole personRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "personRole/create";
        }
        repository.save(personRole);
        return "redirect:/mvc/personRole/read";
    }

    @GetMapping("/update/{id}")
    public String personRoleUpdate(@PathVariable("id") Long id, Model model) {
        PersonRole personRole = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        model.addAttribute("personRole", personRole);
        return "personRole/update";
    }

    @PostMapping("/update")
    public String personRoleUpdateSave(@ModelAttribute("personRole") PersonRole personRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "personRole/update";
        }
        repository.save(personRole);
        return "redirect:/mvc/personRole/read";
    }

    @GetMapping("/delete/{id}")
    public String personRoleDelete(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/mvc/personRole/read";
    }
}