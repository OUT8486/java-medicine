package com.example.spring_boot.controller;

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
    
    @GetMapping({"/inventory-form.html", "/inventory-form"})
    public String inventoryForm() { return "inventory-form"; }
    
    @GetMapping({"/warehouse-form.html", "/warehouse-form"})
    public String warehouseForm() { return "warehouse-form"; }
    
    @GetMapping({"/purchase-order-form.html", "/purchase-order-form"})
    public String purchaseOrderForm() { return "purchase-order-form"; }
    
    @GetMapping({"/sales-order-form.html", "/sales-order-form"})
    public String salesOrderForm() { return "sales-order-form"; }
}