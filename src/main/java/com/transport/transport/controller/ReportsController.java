package com.transport.transport.controller;

import com.transport.transport.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final TransportService transportService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("totalTransports", transportService.getTotalTransports());
        model.addAttribute("totalIncome", transportService.getTotalIncomePaid());

        model.addAttribute("driverStats", transportService.getTransportsCountPerDriver());

        model.addAttribute("driverIncome", transportService.getIncomePerDriver());

        return "reports/list";
    }

    @GetMapping("/period")
    public String period(@RequestParam LocalDate start,
            @RequestParam LocalDate end,
            Model model) {

        double income = transportService.getIncomeForPeriod(start, end);
        model.addAttribute("income", income);

        model.addAttribute("driverIncomePeriod", transportService.getIncomePerDriverForPeriod(start, end));

        return "reports/period";
    }
}
