package com.transport.transport.controller;

import com.transport.transport.model.Company;
import com.transport.transport.service.CompanyService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public String listCompanies(
            @RequestParam(required = false) String sort,
            Model model) {

        if ("name".equals(sort)) {
            model.addAttribute("companies", companyService.findAllSortedByName());
        } else if ("revenue".equals(sort)) {
            model.addAttribute("companies", companyService.findAllSortedByRevenue());
        } else {
            model.addAttribute("companies", companyService.findAll());
        }

        return "companies/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("company", new Company());
        return "companies/form";
    }

    @PostMapping
    public String save(@ModelAttribute Company company) {
        companyService.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.findById(id));
        return "companies/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        companyService.delete(id);
        return "redirect:/companies";
    }
}
