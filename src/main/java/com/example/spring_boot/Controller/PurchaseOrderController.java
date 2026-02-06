package com.example.spring_boot.controller;

import com.example.spring_boot.dao.PurchaseOrderMapper;
import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    
    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    // 1. 页面跳转：访问 http://localhost:8080/purchase-order 跳转到采购订单管理页面
    @GetMapping("/purchase-order")
    public String purchaseOrderPage() {
        return "purchase-order";
    }
    
    // 1.5 页面跳转：访问 http://localhost:8080/purchase-order/form 跳转到采购订单表单页面
    @GetMapping("/purchase-order/form")
    public String purchaseOrderFormPage() {
        return "purchase-order-form";
    }

    // 2. 接口：获取所有采购订单数据
    @GetMapping("/purchase-order/list")
    @ResponseBody
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderMapper.selectAllPurchaseOrders();
    }

    // 4. 接口：根据ID获取采购订单
    @GetMapping("/purchase-order/{id}")
    @ResponseBody
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable String id) {
        PurchaseOrder order = purchaseOrderMapper.selectPurchaseOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 9. 接口：新增采购订单
    @PostMapping("/purchase-order")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        try {
            // 设置订单状态为待收货
            purchaseOrder.setAudit_status(0);  // 修复：使用Integer类型而不是String
            
            int result = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "采购订单添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "采购订单添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：更新采购订单
    @PutMapping("/purchase-order/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updatePurchaseOrder(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder) {
        try {
            // 不要覆盖ID，保持原有的ID
            // purchaseOrder.setPo_id(id);  // 注释掉这行，避免覆盖传入的ID
            purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 13. 接口：删除采购订单
    @DeleteMapping("/purchase-order/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletePurchaseOrder(@PathVariable String id) {
        try {
            // 先删除订单项
            purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(id);
            // 再删除订单
            purchaseOrderMapper.deletePurchaseOrder(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}