package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.Warehouse;
import com.example.spring_boot.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仓库管理控制器
 */
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 获取所有仓库列表
     * GET /api/warehouses
     */
    @GetMapping
    public Result<List<Warehouse>> list() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return Result.success(warehouses);
    }

    /**
     * 根据 ID 获取仓库详情
     * GET /api/warehouses/{id}
     */
    @GetMapping("/{id}")
    public Result<Warehouse> getById(@PathVariable String id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        if (warehouse != null) {
            return Result.success(warehouse);
        } else {
            return Result.error(404, "仓库不存在");
        }
    }


    /**
     * 新增仓库
     * POST /api/warehouses
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody Warehouse warehouse) {
        if (warehouse == null) {
            return Result.error(400, "请求体为空");
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            return Result.error(400, "仓库名称不能为空");
        }

        try {
            int result = warehouseService.addWarehouse(warehouse);
            if (result > 0) {
                return Result.success("仓库添加成功");
            } else {
                return Result.error("仓库添加失败");
            }
        } catch (Exception e) {
            log.error("仓库添加失败", e);
            return Result.error(500, "仓库添加失败，请稍后重试");
        }
    }

    /**
     * 修改仓库信息
     * PUT /api/warehouses/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody Warehouse warehouse) {
        if (warehouse == null) {
            return Result.error(400, "请求体为空");
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            return Result.error(400, "仓库名称不能为空");
        }

        try {
            warehouse.setWarehouseId(id);
            warehouseService.updateWarehouse(warehouse);
            return Result.success("仓库更新成功");
        } catch (Exception e) {
            log.error("仓库更新失败", e);
            return Result.error(500, "仓库更新失败，请稍后重试");
        }
    }

    /**
     * 删除仓库
     * DELETE /api/warehouses/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            warehouseService.deleteWarehouse(id);
            return Result.success("仓库删除成功");
        } catch (Exception e) {
            log.error("仓库删除失败", e);
            return Result.error(500, "仓库删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<Warehouse>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(warehouseService.getWarehousesPage(page, size));
    }

}