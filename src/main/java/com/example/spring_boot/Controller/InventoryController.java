package com.example.spring_boot.controller;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InventoryController {

    @Autowired
    private InventoryMapper inventoryMapper;

    // 1. 页面跳转：访问 http://localhost:8080/inventory 跳转到库存管理页面
    @GetMapping("/inventory")
    public String inventoryPage() {
        return "inventory";
    }
    
    // 1.5 页面跳转：访问 http://localhost:8080/inventory/form 跳转到库存表单页面
    @GetMapping("/inventory/form")
    public String inventoryFormPage() {
        return "inventory-form";
    }

    // 2. 接口：获取所有库存数据
    @GetMapping("/inventory/list")
    @ResponseBody
    public List<Inventory> getAllInventories() {
        return inventoryMapper.selectAllInventories();
    }

    // 4. 接口：根据ID获取库存
    @GetMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Inventory inventory = inventoryMapper.selectInventoryById(id);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 11. 接口：新增库存
    @PostMapping("/inventory")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addInventory(@RequestBody Inventory inventory) {
        try {
            int result = inventoryMapper.insertInventory(inventory);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "库存添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "库存添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 12. 接口：更新库存信息
    @PutMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateInventory(@PathVariable String id, @RequestBody Inventory inventory) {
        try {
            inventory.setInventory_id(id);
            inventoryMapper.updateInventory(inventory);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 14. 接口：删除库存
    @DeleteMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteInventory(@PathVariable String id) {
        try {
            inventoryMapper.deleteInventory(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}