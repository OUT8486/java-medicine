package com.example.spring_boot.controller;

import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.entity.PurchaseOrderItem;
import com.example.spring_boot.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购订单项管理控制器
 */
@RestController
@RequestMapping("/api/purchase-order-items")
@CrossOrigin(origins = "*")
public class PurchaseOrderItemController {

    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    /**
     * 根据采购订单 ID 获取订单项列表
     * GET /api/purchase-order-items?poId={poId}
     */
    @GetMapping
    public Result<List<PurchaseOrderItem>> list(@RequestParam String poId) {
        List<PurchaseOrderItem> items = purchaseOrderItemMapper.selectPurchaseOrderItemsByPoId(poId);
        return Result.success(items);
    }

    /**
     * 根据 ID 获取采购订单项详情
     * GET /api/purchase-order-items/{id}
     */
    @GetMapping("/{id}")
    public Result<PurchaseOrderItem> getById(@PathVariable String id) {
        PurchaseOrderItem item = purchaseOrderItemMapper.selectPurchaseOrderItemById(id);
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
    @PostMapping
    public Result<String> add(@RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            int result = purchaseOrderItemMapper.insertPurchaseOrderItem(purchaseOrderItem);
            if (result > 0) {
                return Result.success("采购订单项添加成功");
            } else {
                return Result.error("采购订单项添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "采购订单项添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改采购订单项信息
     * PUT /api/purchase-order-items/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            purchaseOrderItem.setPoi_id(id);
            int result = purchaseOrderItemMapper.updatePurchaseOrderItem(purchaseOrderItem);
            if (result > 0) {
                return Result.success("采购订单项更新成功");
            } else {
                return Result.error("采购订单项更新失败");
            }
        } catch (Exception e) {
            return Result.error(500, "采购订单项更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除采购订单项
     * DELETE /api/purchase-order-items/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            int result = purchaseOrderItemMapper.deletePurchaseOrderItem(id);
            if (result > 0) {
                return Result.success("采购订单项删除成功");
            } else {
                return Result.error("采购订单项删除失败");
            }
        } catch (Exception e) {
            return Result.error(500, "采购订单项删除失败：" + e.getMessage());
        }
    }

    /**
     * 根据采购订单 ID 删除所有订单项
     * DELETE /api/purchase-order-items/batch?poId={poId}
     */
    @DeleteMapping("/batch")
    public Result<String> deleteByPoId(@RequestParam String poId) {
        try {
            purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(poId);
            return Result.success("采购订单项批量删除成功");
        } catch (Exception e) {
            return Result.error(500, "采购订单项批量删除失败：" + e.getMessage());
        }
    }
}