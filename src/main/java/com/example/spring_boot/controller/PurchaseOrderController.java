package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.PurchaseOrder;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购订单管理控制器
 */
@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 获取所有采购订单列表
     * GET /api/purchase-orders
     */
    @GetMapping
    public Result<List<PurchaseOrder>> list() {
        List<PurchaseOrder> orders = purchaseOrderService.getAllPurchaseOrders();
        return Result.success(orders);
    }

    /**
     * 根据 ID 获取采购订单详情
     * GET /api/purchase-orders/{id}
     */
    @GetMapping("/{id}")
    public Result<PurchaseOrder> getById(@PathVariable String id) {
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(id);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error(404, "采购订单不存在，ID: " + id);
        }
    }

    /**
     * 新增采购订单
     * POST /api/purchase-orders
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody PurchaseOrder purchaseOrder) {
        try {
            // 设置订单状态为待收货
            purchaseOrder.setAuditStatus(0);
            
            int result = purchaseOrderService.addPurchaseOrder(purchaseOrder);
            if (result > 0) {
                return Result.success("采购订单添加成功");
            } else {
                return Result.error("采购订单添加失败");
            }
        } catch (Exception e) {
            log.error("采购订单添加失败", e);
            return Result.error(500, "采购订单添加失败，请稍后重试");
        }
    }

    /**
     * 修改采购订单信息
     * PUT /api/purchase-orders/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody PurchaseOrder purchaseOrder) {
        try {
            purchaseOrder.setPoId(id);
            purchaseOrderService.updatePurchaseOrder(purchaseOrder);
            return Result.success("采购订单更新成功");
        } catch (Exception e) {
            log.error("采购订单更新失败", e);
            return Result.error(500, "采购订单更新失败，请稍后重试");
        }
    }

    /**
     * 删除采购订单
     * DELETE /api/purchase-orders/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            // 先删除订单项，再删除订单
            purchaseOrderService.deletePurchaseOrderWithItems(id);
            return Result.success("采购订单删除成功");
        } catch (Exception e) {
            log.error("采购订单删除失败", e);
            return Result.error(500, "采购订单删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<PurchaseOrder>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(purchaseOrderService.getPurchaseOrdersPage(page, size));
    }

}
