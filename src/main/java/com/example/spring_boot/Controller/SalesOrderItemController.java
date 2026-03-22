package com.example.spring_boot.controller;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售订单项管理控制器
 */
@RestController
@RequestMapping("/api/sales-order-items")
@CrossOrigin(origins = "*")
public class SalesOrderItemController {

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 根据销售订单 ID 获取订单项列表
     * GET /api/sales-order-items?soId={soId}
     */
    @GetMapping
    public Result<List<SalesOrderItem>> list(@RequestParam String soId) {
        List<SalesOrderItem> items = salesOrderItemMapper.selectSalesOrderItemsBySoId(soId);
        return Result.success(items);
    }

    /**
     * 根据 ID 获取销售订单项详情
     * GET /api/sales-order-items/{id}
     */
    @GetMapping("/{id}")
    public Result<SalesOrderItem> getById(@PathVariable String id) {
        SalesOrderItem item = salesOrderItemMapper.selectSalesOrderItemById(id);
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
    @PostMapping
    public Result<String> add(@RequestBody SalesOrderItem salesOrderItem) {
        try {
            int result = salesOrderItemMapper.insertSalesOrderItem(salesOrderItem);
            if (result > 0) {
                return Result.success("销售订单项添加成功");
            } else {
                return Result.error("销售订单项添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "销售订单项添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改销售订单项信息
     * PUT /api/sales-order-items/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody SalesOrderItem salesOrderItem) {
        try {
            salesOrderItem.setSoi_id(id);
            int result = salesOrderItemMapper.updateSalesOrderItem(salesOrderItem);
            if (result > 0) {
                return Result.success("销售订单项更新成功");
            } else {
                return Result.error("销售订单项更新失败");
            }
        } catch (Exception e) {
            return Result.error(500, "销售订单项更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除销售订单项
     * DELETE /api/sales-order-items/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            int result = salesOrderItemMapper.deleteSalesOrderItem(id);
            if (result > 0) {
                return Result.success("销售订单项删除成功");
            } else {
                return Result.error("销售订单项删除失败");
            }
        } catch (Exception e) {
            return Result.error(500, "销售订单项删除失败：" + e.getMessage());
        }
    }

    /**
     * 根据销售订单 ID 删除所有订单项
     * DELETE /api/sales-order-items/batch?soId={soId}
     */
    @DeleteMapping("/batch")
    public Result<String> deleteBySoId(@RequestParam String soId) {
        try {
            salesOrderItemMapper.deleteSalesOrderItemsBySoId(soId);
            return Result.success("销售订单项批量删除成功");
        } catch (Exception e) {
            return Result.error(500, "销售订单项批量删除失败：" + e.getMessage());
        }
    }
}