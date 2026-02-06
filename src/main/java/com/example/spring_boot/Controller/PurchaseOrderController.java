package com.example.spring_boot.controller;

import com.example.spring_boot.dao.PurchaseOrderMapper;
import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
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

    // 简化版采购订单页面
    @GetMapping("/purchase-simple")
    public String purchaseSimplePage() {
        return "purchase-simple";
    }

    // 调试页面路由
    @GetMapping("/debug-purchase")
    public String debugPurchasePage() {
        return "debug-purchase";
    }

    // 2. 接口：获取所有采购订单数据
    @GetMapping("/purchase-order/list")
    @ResponseBody
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderMapper.selectAllPurchaseOrders();
    }

    // 3. 接口：分页查询采购订单数据
    @GetMapping("/purchase-order/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPurchaseOrdersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String supplierId,
            @RequestParam(required = false) Integer auditStatus) {
        
        // 获取采购订单列表
        List<PurchaseOrder> orders = purchaseOrderMapper.selectAllPurchaseOrders();
        
        // 应用过滤条件
        if (search != null && !search.trim().isEmpty()) {
            // 这里可以根据需要添加搜索逻辑
        }
        if (supplierId != null && !supplierId.trim().isEmpty()) {
            orders = purchaseOrderMapper.selectPurchaseOrdersBySupplierId(supplierId);
        }
        if (auditStatus != null) {
            orders = purchaseOrderMapper.selectPurchaseOrdersByAuditStatus(auditStatus);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders);
        response.put("total", orders.size());
        response.put("page", page);
        response.put("size", size);
        
        return ResponseEntity.ok(response);
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

    // 5. 接口：根据供应商ID查询采购订单
    @GetMapping("/purchase-order/supplier/{supplierId}")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersBySupplierId(@PathVariable String supplierId) {
        List<PurchaseOrder> orders = purchaseOrderMapper.selectPurchaseOrdersBySupplierId(supplierId);
        return ResponseEntity.ok(orders);
    }

    // 6. 接口：根据状态查询采购订单
    @GetMapping("/purchase-order/status/{status}")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByStatus(@PathVariable String status) {
        List<PurchaseOrder> orders = purchaseOrderMapper.selectPurchaseOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // 7. 接口：根据创建人查询采购订单
    @GetMapping("/purchase-order/creator/{createBy}")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByCreateBy(@PathVariable String createBy) {
        List<PurchaseOrder> orders = purchaseOrderMapper.selectPurchaseOrdersByCreateBy(createBy);
        return ResponseEntity.ok(orders);
    }

    // 8. 接口：根据日期范围查询采购订单
    @GetMapping("/purchase-order/date-range")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByDateRange(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<PurchaseOrder> orders = purchaseOrderMapper.selectPurchaseOrdersByDateRangeDate(startDate, endDate);
        return ResponseEntity.ok(orders);
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

    // 11. 接口：更新采购订单状态
    @PutMapping("/purchase-order/{id}/status")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updatePurchaseOrderStatus(
            @PathVariable String id, 
            @RequestBody Map<String, Object> statusData) {
        try {
            Object statusObj = statusData.get("status");
            if (statusObj == null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "状态不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            String status;
            if (statusObj instanceof Integer) {
                status = ((Integer) statusObj).toString();
            } else {
                status = statusObj.toString();
            }
            purchaseOrderMapper.updatePurchaseOrderAuditStatus(id, status);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单状态更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单状态更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 12. 接口：计算订单总金额
    @GetMapping("/purchase-order/{id}/total")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> calculateOrderTotal(@PathVariable String id) {
        try {
            BigDecimal total = purchaseOrderMapper.calculateOrderTotal(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("total", total);
            response.put("message", "订单总金额计算成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "订单总金额计算失败：" + e.getMessage());
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