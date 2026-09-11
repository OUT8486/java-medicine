package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SalesOrderController {

    private static final Logger log = LoggerFactory.getLogger(SalesOrderController.class);

    @Autowired
    private SalesOrderService salesOrderService;

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
        SalesOrder order = salesOrderService.getSalesOrderById(id);
        if (order != null) {
        } else {
        }
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
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody SalesOrder salesOrder) {
        try {
            int result = salesOrderService.addSalesOrder(salesOrder);
            if (result > 0) {
                return Result.success("销售订单添加成功");
            } else {
                return Result.error("销售订单添加失败");
            }
        } catch (Exception e) {
            log.error("销售订单添加失败", e);
            return Result.error(500, "销售订单添加失败，请稍后重试");
        }
    }

    /**
     * 修改销售订单信息
     * PUT /api/sales-orders/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody SalesOrder salesOrder) {
        try {
            salesOrder.setSo_id(id);
            salesOrderService.updateSalesOrder(salesOrder);
            return Result.success("销售订单更新成功");
        } catch (Exception e) {
            log.error("销售订单更新失败", e);
            return Result.error(500, "销售订单更新失败，请稍后重试");
        }
    }

    /**
     * 删除销售订单
     * DELETE /api/sales-orders/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            // 先删除订单项，再删除订单
            salesOrderService.deleteSalesOrderWithItems(id);
            return Result.success("销售订单删除成功");
        } catch (Exception e) {
            log.error("销售订单删除失败", e);
            return Result.error(500, "销售订单删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<SalesOrder>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(salesOrderService.getSalesOrdersPage(page, size));
    }

}
