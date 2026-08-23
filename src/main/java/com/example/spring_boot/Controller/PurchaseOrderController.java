package com.example.spring_boot.controller;

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
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

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
        System.out.println("========================================");
        System.out.println("=== 接收到查询请求，ID: " + id + " ===");
        System.out.println("=== ID 长度：" + (id != null ? id.length() : "null") + " ===");
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(id);
        if (order != null) {
            System.out.println("=== 查询结果：找到订单，PO_ID: " + order.getPo_id() + " ===");
        } else {
            System.out.println("=== 查询结果：未找到订单 ===");
        }
        System.out.println("========================================");
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
    @PostMapping
    public Result<String> add(@RequestBody PurchaseOrder purchaseOrder) {
        try {
            // 设置订单状态为待收货
            purchaseOrder.setAudit_status(0);
            
            int result = purchaseOrderService.addPurchaseOrder(purchaseOrder);
            if (result > 0) {
                return Result.success("采购订单添加成功");
            } else {
                return Result.error("采购订单添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "采购订单添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改采购订单信息
     * PUT /api/purchase-orders/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder) {
        try {
            purchaseOrder.setPo_id(id);
            purchaseOrderService.updatePurchaseOrder(purchaseOrder);
            return Result.success("采购订单更新成功");
        } catch (Exception e) {
            return Result.error(500, "采购订单更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除采购订单
     * DELETE /api/purchase-orders/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            // 先删除订单项，再删除订单
            purchaseOrderService.deletePurchaseOrderWithItems(id);
            return Result.success("采购订单删除成功");
        } catch (Exception e) {
            return Result.error(500, "采购订单删除失败：" + e.getMessage());
        }
    }
}
