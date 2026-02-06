package com.example.spring_boot.controller;

import com.example.spring_boot.dao.SalesOrderMapper;
import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.entity.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SalesOrderController {

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    // 1. 页面跳转：访问 http://localhost:8080/sales-order 跳转到销售订单管理页面
    @GetMapping("/sales-order")
    public String salesOrderPage() {
        return "sales-order";
    }
    
    // 1.5 页面跳转：访问 http://localhost:8080/sales-order/form 跳转到销售订单表单页面
    @GetMapping("/sales-order/form")
    public String salesOrderFormPage() {
        return "sales-order-form";
    }

    // 2. 接口：获取所有销售订单数据
    @GetMapping("/sales-order/list")
    @ResponseBody
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderMapper.selectAllSalesOrders();
    }

    // 3. 接口：分页查询销售订单数据
    @GetMapping("/sales-order/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSalesOrdersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String employeeId) {
        
        List<SalesOrder> orders;
        
        // 根据查询条件选择不同的查询方式
        if (customerId != null && !customerId.isEmpty()) {
            // 按客户ID查询
            orders = salesOrderMapper.selectSalesOrdersByCustomerId(customerId);
        } else if (employeeId != null && !employeeId.isEmpty()) {
            // 按员工ID查询
            orders = salesOrderMapper.selectSalesOrdersByCreateBy(employeeId);
        } else {
            // 查询所有订单
            orders = salesOrderMapper.selectAllSalesOrders();
        }
        
        // 如果有搜索关键词，进行客户端过滤
        if (search != null && !search.isEmpty()) {
            final String searchTerm = search.toLowerCase();
            orders = orders.stream().filter(order -> {
                return (order.getSo_id() != null && order.getSo_id().toLowerCase().contains(searchTerm)) ||
                       (order.getCustomer_id() != null && order.getCustomer_id().toLowerCase().contains(searchTerm)) ||
                       (order.getEmployee_id() != null && order.getEmployee_id().toLowerCase().contains(searchTerm));
            }).collect(java.util.stream.Collectors.toList());
        }
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("orders", orders);
        result.put("total", orders.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
    }

    // 4. 接口：根据ID获取销售订单
    @GetMapping("/sales-order/{id}")
    @ResponseBody
    public ResponseEntity<SalesOrder> getSalesOrderById(@PathVariable String id) {
        SalesOrder order = salesOrderMapper.selectSalesOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 9. 接口：新增销售订单
    @PostMapping("/sales-order")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSalesOrder(@RequestBody SalesOrder salesOrder) {
        try {
            int result = salesOrderMapper.insertSalesOrder(salesOrder);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "销售订单添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "销售订单添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：更新销售订单
    @PutMapping("/sales-order/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateSalesOrder(@PathVariable String id, @RequestBody SalesOrder salesOrder) {
        try {
            salesOrder.setSo_id(id);
            salesOrderMapper.updateSalesOrder(salesOrder);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 13. 接口：删除销售订单
    @DeleteMapping("/sales-order/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSalesOrder(@PathVariable String id) {
        try {
            // 先删除订单项
            salesOrderItemMapper.deleteSalesOrderItemsBySoId(id);
            // 再删除订单
            salesOrderMapper.deleteSalesOrder(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}