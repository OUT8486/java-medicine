package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.SalesOrderItem;
import com.example.spring_boot.service.SalesOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售订单项管理控制器
 */
@RestController
@RequestMapping("/api/sales-order-items")
public class SalesOrderItemController {

    private static final Logger log = LoggerFactory.getLogger(SalesOrderItemController.class);

    @Autowired
    private SalesOrderItemService salesOrderItemService;

    /**
     * 根据销售订单 ID 获取订单项列表
     * GET /api/sales-order-items?soId={soId}
     */
    @GetMapping
    public Result<List<SalesOrderItem>> list(@RequestParam String soId) {
        List<SalesOrderItem> items = salesOrderItemService.getSalesOrderItemsBySoId(soId);
        return Result.success(items);
    }

    /**
     * 根据 ID 获取销售订单项详情
     * GET /api/sales-order-items/{id}
     */
    @GetMapping("/{id}")
    public Result<SalesOrderItem> getById(@PathVariable String id) {
        SalesOrderItem item = salesOrderItemService.getSalesOrderItemById(id);
        if (item != null) {
            return Result.success(item);
        } else {
            return Result.error(404, "销售订单项不存在");
        }
    }

    /**
     * 新增销售订单项
     * POST /api/sales-order-items
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody SalesOrderItem salesOrderItem) {
        try {
            int result = salesOrderItemService.addSalesOrderItem(salesOrderItem);
            if (result > 0) {
                return Result.success("销售订单项添加成功");
            } else {
                return Result.error("销售订单项添加失败");
            }
        } catch (Exception e) {
            log.error("销售订单项添加失败", e);
            return Result.error(500, "销售订单项添加失败，请稍后重试");
        }
    }

    /**
     * 修改销售订单项信息
     * PUT /api/sales-order-items/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody SalesOrderItem salesOrderItem) {
        try {
            salesOrderItem.setSoiId(id);
            salesOrderItemService.updateSalesOrderItem(salesOrderItem);
            return Result.success("销售订单项更新成功");
        } catch (Exception e) {
            log.error("销售订单项更新失败", e);
            return Result.error(500, "销售订单项更新失败，请稍后重试");
        }
    }

    /**
     * 删除销售订单项
     * DELETE /api/sales-order-items/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            salesOrderItemService.deleteSalesOrderItem(id);
            return Result.success("销售订单项删除成功");
        } catch (Exception e) {
            log.error("销售订单项删除失败", e);
            return Result.error(500, "销售订单项删除失败，请稍后重试");
        }
    }

    /**
     * 根据销售订单 ID 删除所有订单项
     * DELETE /api/sales-order-items/batch?soId={soId}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/batch")
    public Result<String> deleteBySoId(@RequestParam String soId) {
        try {
            salesOrderItemService.deleteSalesOrderItemsBySoId(soId);
            return Result.success("销售订单项批量删除成功");
        } catch (Exception e) {
            log.error("销售订单项批量删除失败", e);
            return Result.error(500, "销售订单项批量删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<SalesOrderItem>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(salesOrderItemService.getSalesOrderItemsPage(page, size));
    }

}