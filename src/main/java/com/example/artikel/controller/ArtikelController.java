package com.example.artikel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.artikel.model.Artikel;
import com.example.artikel.repository.ArtikelRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArtikelController {
    @Autowired
    private ArtikelRepository artikelRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("artikels", artikelRepository.findAll());
        return "index";
    }

    @GetMapping("/submit")
    public String showSubmitForm(Model model) {
        model.addAttribute("artikel", new Artikel());
        return "submit";
    }

    @PostMapping("/submit")
    public String submitArtikel(@ModelAttribute Artikel artikel) {
        artikelRepository.save(artikel);
        return "redirect:/";
    }

}
