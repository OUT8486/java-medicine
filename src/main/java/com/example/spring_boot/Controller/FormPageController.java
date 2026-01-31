package com.example.spring_boot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormPageController {

    @GetMapping({"/supplier-form.html", "/supplier-form"})
    public String supplierForm() { return "supplier-form"; }

    @GetMapping({"/drug-form.html", "/drug-form"})
    public String drugForm() { return "drug-form"; }

    @GetMapping({"/employee-form.html", "/employee-form"})
    public String employeeForm() { return "employee-form"; }

    @GetMapping({"/customer-form.html", "/customer-form"})
    public String customerForm() { return "customer-form"; }
}
