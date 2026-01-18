package com.transport.transport.controller;

import com.transport.transport.model.Vehicle;
import com.transport.transport.service.CompanyService;
import com.transport.transport.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final CompanyService companyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehicles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("companies", companyService.findAll());
        return "vehicles/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.findById(id);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("companies", companyService.findAll());
        return "vehicles/form";
    }

    @PostMapping
    public String save(@ModelAttribute Vehicle vehicle) {
        vehicleService.save(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }
}
