package com.transport.transport.controller;

import com.transport.transport.model.Client;
import com.transport.transport.service.ClientService;
import com.transport.transport.service.CompanyService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final CompanyService companyService;

    private final ClientService clientService;

    public ClientController(ClientService clientService, CompanyService companyService) {
        this.clientService = clientService;
        this.companyService = companyService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("companies", companyService.findAll());
        return "clients/form";
    }

    @PostMapping
    public String save(@ModelAttribute Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Client client = clientService.findById(id);
        model.addAttribute("client", client);
        model.addAttribute("companies", companyService.findAll());
        return "clients/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }

    @GetMapping("/pay/{id}")
    public String markAsPaid(@PathVariable Long id) {
        clientService.markAsPaid(id);
        return "redirect:/clients";
    }
}
