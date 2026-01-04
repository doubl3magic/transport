package com.transport.transport.controller;

import com.transport.transport.model.Employee;
import com.transport.transport.service.CompanyService;
import com.transport.transport.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping
    public String list(
            @RequestParam(required = false) String qualification,
            @RequestParam(required = false) String salary,
            Model model) {

        if (qualification != null) {
            model.addAttribute("employees",
                    employeeService.findByQualification(qualification));
        } else if (salary != null) {
            model.addAttribute("employees",
                    employeeService.findSortedBySalary());
        } else {
            model.addAttribute("employees", employeeService.findAll());
        }

        return "employees/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companies", companyService.findAll());
        return "employees/form";
    }

    @PostMapping
    public String save(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
