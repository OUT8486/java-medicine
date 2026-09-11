package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.Inventory;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理控制器
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    /**
     * 获取所有库存列表
     * GET /api/inventory
     */
    @GetMapping
    public Result<List<Inventory>> list() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        return Result.success(inventories);
    }

    /**
     * 根据 ID 获取库存详情
     * GET /api/inventory/{id}
     */
    @GetMapping("/{id}")
    public Result<Inventory> getById(@PathVariable String id) {
        Inventory inventory = inventoryService.getInventoryById(id);
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
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody Inventory inventory) {
        try {
            int result = inventoryService.addInventory(inventory);
            if (result > 0) {
                return Result.success("库存添加成功");
            } else {
                return Result.error("库存添加失败");
            }
        } catch (Exception e) {
            log.error("库存添加失败", e);
            return Result.error(500, "库存添加失败，请稍后重试");
        }
    }

    /**
     * 修改库存信息
     * PUT /api/inventory/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody Inventory inventory) {
        try {
            inventory.setInventoryId(id);
            inventoryService.updateInventory(inventory);
            return Result.success("库存更新成功");
        } catch (Exception e) {
            log.error("库存更新失败", e);
            return Result.error(500, "库存更新失败，请稍后重试");
        }
    }

    /**
     * 删除库存
     * DELETE /api/inventory/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            inventoryService.deleteInventory(id);
            return Result.success("库存删除成功");
        } catch (Exception e) {
            log.error("库存删除失败", e);
            return Result.error(500, "库存删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<Inventory>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(inventoryService.getInventoriesPage(page, size));
    }

}