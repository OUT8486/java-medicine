package com.example.spring_boot.controller;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
import com.example.spring_boot.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理控制器
 */
@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 获取所有库存列表
     * GET /api/inventory
     */
    @GetMapping
    public Result<List<Inventory>> list() {
        List<Inventory> inventories = inventoryMapper.selectAllInventories();
        return Result.success(inventories);
    }

    /**
     * 根据 ID 获取库存详情
     * GET /api/inventory/{id}
     */
    @GetMapping("/{id}")
    public Result<Inventory> getById(@PathVariable String id) {
        Inventory inventory = inventoryMapper.selectInventoryById(id);
        if (inventory != null) {
            return Result.success(inventory);
        } else {
            return Result.error(404, "库存记录不存在");
        }
    }

    /**
     * 新增库存
     * POST /api/inventory
     */
    @PostMapping
    public Result<String> add(@RequestBody Inventory inventory) {
        try {
            int result = inventoryMapper.insertInventory(inventory);
            if (result > 0) {
                return Result.success("库存添加成功");
            } else {
                return Result.error("库存添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "库存添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改库存信息
     * PUT /api/inventory/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody Inventory inventory) {
        try {
            inventory.setInventory_id(id);
            int result = inventoryMapper.updateInventory(inventory);
            if (result > 0) {
                return Result.success("库存更新成功");
            } else {
                return Result.error("库存更新失败");
            }
        } catch (Exception e) {
            return Result.error(500, "库存更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除库存
     * DELETE /api/inventory/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            int result = inventoryMapper.deleteInventory(id);
            if (result > 0) {
                return Result.success("库存删除成功");
            } else {
                return Result.error("库存删除失败");
            }
        } catch (Exception e) {
            return Result.error(500, "库存删除失败：" + e.getMessage());
        }
    }
}