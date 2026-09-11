package com.example.spring_boot.controller;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.PurchaseOrderItem;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.PurchaseOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购订单项管理控制器
 */
@RestController
@RequestMapping("/api/purchase-order-items")
public class PurchaseOrderItemController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderItemController.class);

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    /**
     * 根据采购订单 ID 获取订单项列表
     * GET /api/purchase-order-items?poId={poId}
     */
    @GetMapping
    public Result<List<PurchaseOrderItem>> list(@RequestParam String poId) {
        List<PurchaseOrderItem> items = purchaseOrderItemService.getPurchaseOrderItemsByPoId(poId);
        return Result.success(items);
    }

    /**
     * 根据 ID 获取采购订单项详情
     * GET /api/purchase-order-items/{id}
     */
    @GetMapping("/{id}")
    public Result<PurchaseOrderItem> getById(@PathVariable String id) {
        PurchaseOrderItem item = purchaseOrderItemService.getPurchaseOrderItemById(id);
        if (item != null) {
            return Result.success(item);
        } else {
            return Result.error(404, "采购订单项不存在");
        }
    }

    /**
     * 新增采购订单项
     * POST /api/purchase-order-items
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            int result = purchaseOrderItemService.addPurchaseOrderItem(purchaseOrderItem);
            if (result > 0) {
                return Result.success("采购订单项添加成功");
            } else {
                return Result.error("采购订单项添加失败");
            }
        } catch (Exception e) {
            log.error("采购订单项添加失败", e);
            return Result.error(500, "采购订单项添加失败，请稍后重试");
        }
    }

    /**
     * 修改采购订单项信息
     * PUT /api/purchase-order-items/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            purchaseOrderItem.setPoi_id(id);
            purchaseOrderItemService.updatePurchaseOrderItem(purchaseOrderItem);
            return Result.success("采购订单项更新成功");
        } catch (Exception e) {
            log.error("采购订单项更新失败", e);
            return Result.error(500, "采购订单项更新失败，请稍后重试");
        }
    }

    /**
     * 删除采购订单项
     * DELETE /api/purchase-order-items/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            purchaseOrderItemService.deletePurchaseOrderItem(id);
            return Result.success("采购订单项删除成功");
        } catch (Exception e) {
            log.error("采购订单项删除失败", e);
            return Result.error(500, "采购订单项删除失败，请稍后重试");
        }
    }

    /**
     * 根据采购订单 ID 删除所有订单项
     * DELETE /api/purchase-order-items/batch?poId={poId}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/batch")
    public Result<String> deleteByPoId(@RequestParam String poId) {
        try {
            purchaseOrderItemService.deletePurchaseOrderItemsByPoId(poId);
            return Result.success("采购订单项批量删除成功");
        } catch (Exception e) {
            log.error("采购订单项批量删除失败", e);
            return Result.error(500, "采购订单项批量删除失败，请稍后重试");
        }
    }
}