package com.example.spring_boot.controller;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.SalesOrder;
import com.example.spring_boot.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售订单管理控制器
 */
@RestController
@RequestMapping("/api/sales-orders")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;
    
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 获取所有销售订单列表
     * GET /api/sales-orders
     */
    @GetMapping
    public Result<List<SalesOrder>> list() {
        List<SalesOrder> orders = salesOrderService.getAllSalesOrders();
        return Result.success(orders);
    }

    /**
     * 根据客户 ID 查询销售订单列表
     * GET /api/sales-orders/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public Result<List<SalesOrder>> getByCustomerId(@PathVariable String customerId) {
        List<SalesOrder> orders = salesOrderService.getSalesOrdersByCustomerId(customerId);
        return Result.success(orders);
    }

    /**
     * 根据员工 ID 查询销售订单列表
     * GET /api/sales-orders/employee/{employeeId}
     */
    @GetMapping("/employee/{employeeId}")
    public Result<List<SalesOrder>> getByEmployeeId(@PathVariable String employeeId) {
        List<SalesOrder> orders = salesOrderService.getSalesOrdersByEmployeeId(employeeId);
        return Result.success(orders);
    }

    /**
     * 根据 ID 获取销售订单详情
     * GET /api/sales-orders/{id}
     */
    @GetMapping("/{id}")
    public Result<SalesOrder> getById(@PathVariable String id) {
        System.out.println("========================================");
        System.out.println("=== 接收到查询请求，ID: " + id + " ===");
        System.out.println("=== ID 长度：" + (id != null ? id.length() : "null") + " ===");
        SalesOrder order = salesOrderService.getSalesOrderById(id);
        if (order != null) {
            System.out.println("=== 查询结果：找到订单，SO_ID: " + order.getSo_id() + " ===");
        } else {
            System.out.println("=== 查询结果：未找到订单 ===");
        }
        System.out.println("========================================");
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error(404, "销售订单不存在，ID: " + id);
        }
    }

    /**
     * 新增销售订单
     * POST /api/sales-orders
     */
    @PostMapping
    public Result<String> add(@RequestBody SalesOrder salesOrder) {
        try {
            int result = salesOrderService.addSalesOrder(salesOrder);
            if (result > 0) {
                return Result.success("销售订单添加成功");
            } else {
                return Result.error("销售订单添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "销售订单添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改销售订单信息
     * PUT /api/sales-orders/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody SalesOrder salesOrder) {
        try {
            salesOrder.setSo_id(id);
            salesOrderService.updateSalesOrder(salesOrder);
            return Result.success("销售订单更新成功");
        } catch (Exception e) {
            return Result.error(500, "销售订单更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除销售订单
     * DELETE /api/sales-orders/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            // 先删除订单项，再删除订单
            salesOrderItemMapper.deleteSalesOrderItemsBySoId(id);
            salesOrderService.deleteSalesOrder(id);
            return Result.success("销售订单删除成功");
        } catch (Exception e) {
            return Result.error(500, "销售订单删除失败：" + e.getMessage());
        }
    }
}