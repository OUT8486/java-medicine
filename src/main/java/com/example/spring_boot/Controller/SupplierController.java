package com.example.spring_boot.controller;

import com.example.spring_boot.dao.SupplierMapper;
import com.example.spring_boot.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SupplierController {

    @Autowired
    private SupplierMapper supplierMapper;

    // 1. 页面跳转：访问 http://localhost:8080/supplier 跳转到供应商管理页面
    @GetMapping("/supplier")
    public String supplierPage() {
        return "supplier";
    }
    
    // 1.5 页面跳转：访问 http://localhost:8080/supplier/form 跳转到供应商表单页面
    @GetMapping("/supplier/form")
    public String supplierFormPage() {
        return "supplier-form";
    }

    // 2. 接口：获取所有供应商数据
    @GetMapping("/supplier/list")
    @ResponseBody
    public List<Supplier> getAllSuppliers() {
        return supplierMapper.selectAllSuppliers();
    }

    // 4. 接口：根据ID获取供应商
    @GetMapping("/supplier/{id}")
    @ResponseBody
    public ResponseEntity<Supplier> getSupplierById(@PathVariable String id) {
        Supplier supplier = supplierMapper.selectSupplierById(id);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 8. 接口：新增供应商
    @PostMapping("/supplier")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSupplier(@RequestBody Supplier supplier) {
        // 基本校验
        if (supplier == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        String phone = supplier.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "contact_phone 必须为 11 位数字");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        Integer status = supplier.getStatus();
        if (status != null && status != 0 && status != 1) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "status 必须为 0 或 1");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            int result = supplierMapper.insertSupplier(supplier);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "供应商添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "供应商添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "供应商添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 9. 接口：更新供应商信息
    @PutMapping("/supplier/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateSupplier(@PathVariable String id, @RequestBody Supplier supplier) {
        // 基本校验
        if (supplier == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        String phone = supplier.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "contact_phone 必须为 11 位数字");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        Integer status = supplier.getStatus();
        if (status != null && status != 0 && status != 1) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "status 必须为 0 或 1");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            supplier.setSupplier_id(id);
            supplierMapper.updateSupplier(supplier);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "供应商更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "供应商更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：删除供应商
    @DeleteMapping("/supplier/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSupplier(@PathVariable String id) {
        try {
            supplierMapper.deleteSupplier(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "供应商删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "供应商删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}