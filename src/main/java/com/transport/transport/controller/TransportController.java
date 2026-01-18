package com.transport.transport.controller;

import com.transport.transport.model.Transport;
import com.transport.transport.service.ClientService;
import com.transport.transport.service.EmployeeService;
import com.transport.transport.service.TransportService;
import com.transport.transport.service.VehicleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/transports")
@RequiredArgsConstructor
public class TransportController {

    private final TransportService transportService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final VehicleService vehicleService;

    @GetMapping
    public String list(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String sort,
            Model model) {

        if (destination != null) {
            model.addAttribute("transports",
                    transportService.findByDestination(destination));
        } else if ("destination".equals(sort)) {
            model.addAttribute("transports",
                    transportService.findAllSortedByDestination());
        } else {
            model.addAttribute("transports",
                    transportService.findAll());
        }

        return "transports/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("transport", new Transport());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("vehicles", vehicleService.findAll());
        return "transports/form";
    }

    @PostMapping
    public String save(@ModelAttribute Transport transport) {
        transportService.save(transport);
        return "redirect:/transports";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export() {
        byte[] data = transportService.exportToBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transports.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(data);
    }

    @GetMapping("/import")
    public String importData() {
        transportService.importFromFile();
        return "redirect:/transports";
    }

    @GetMapping("/pay/{id}")
    public String markTransportPaid(@PathVariable Long id) {
        transportService.markAsPaid(id);
        return "redirect:/transports";
    }
}
