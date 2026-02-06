package com.example.spring_boot.controller;

import com.example.spring_boot.dao.DrugMapper;
import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.DrugWithManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@Controller // 注意：用@Controller，不是@RestController（要返回页面）
public class DrugController {

    @Autowired
    private DrugMapper drugMapper;

    // 1. 页面跳转：访问 http://localhost:8080/drug 跳转到药品展示页面
    @GetMapping("/drug")
    public String drugPage() {
        // 返回 templates 目录下的 drug.html 页面
        return "drug";
    }
    
    // 1.5 页面跳转：访问 http://localhost:8080/drug/form 跳转到药品表单页面
    @GetMapping("/drug/form")
    public String drugFormPage() {
        return "drug-form";
    }

    // 2. 接口：获取所有药品数据（返回JSON）
    @GetMapping("/drug/list")
    @ResponseBody // 标记返回JSON，不是页面
    public List<Drug> getAllDrugs() {
        return drugMapper.selectAllDrugs();
    }

    // 3. 接口：获取单个药品数据
    @GetMapping("/drug/{id}")
    @ResponseBody
    public ResponseEntity<Drug> getDrugById(@PathVariable String id) {
        Drug drug = drugMapper.selectDrugById(id);
        if (drug != null) {
            return ResponseEntity.ok(drug);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 接口：获取药品及其生产厂家信息
    @GetMapping("/drug/{id}/with-manufacturer")
    @ResponseBody
    public ResponseEntity<DrugWithManufacturer> getDrugWithManufacturer(@PathVariable String id) {
        DrugWithManufacturer drug = drugMapper.selectDrugWithManufacturer(id);
        if (drug != null) {
            return ResponseEntity.ok(drug);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. 接口：新增药品
    @PostMapping("/drug")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addDrug(@RequestBody Drug drug) {
        // 基本输入校验，避免触发数据库 CHECK 约束
        if (drug == null) {
            Map<String, String> r = new HashMap<>(); r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (drug.getGeneric_name() == null || drug.getGeneric_name().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); r.put("message", "generic_name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        BigDecimal retail = drug.getRetail_price();
        if (retail == null || retail.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, String> r = new HashMap<>(); r.put("message", "retail_price 必须大于 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        BigDecimal purchase = drug.getPurchase_price();
        if (purchase != null && purchase.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, String> r = new HashMap<>(); r.put("message", "purchase_price 必须大于 0 或为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            int result = drugMapper.insertDrug(drug);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "药品添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "药品添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "药品添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 7. 接口：更新药品信息
    @PutMapping("/drug/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateDrug(@PathVariable String id, @RequestBody Drug drug) {
        // 基本输入校验
        if (drug == null) {
            Map<String, String> r = new HashMap<>(); r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        BigDecimal retail = drug.getRetail_price();
        if (retail != null && retail.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, String> r = new HashMap<>(); r.put("message", "retail_price 必须大于 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        BigDecimal purchase = drug.getPurchase_price();
        if (purchase != null && purchase.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, String> r = new HashMap<>(); r.put("message", "purchase_price 必须大于 0 或为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            drug.setDrug_id(id); // 设置药品ID
            drugMapper.updateDrug(drug);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "药品更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "药品更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 8. 接口：删除药品
    @DeleteMapping("/drug/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteDrug(@PathVariable String id) {
        try {
            drugMapper.deleteDrug(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "药品删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "药品删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}