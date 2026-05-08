package com.example.spring_boot.controller;

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
@CrossOrigin(origins = "*")
public class WarehouseController {

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
     * 根据名称查询仓库
     * GET /api/warehouses/name/{name}
     */
    @GetMapping("/name/{name}")
    public Result<List<Warehouse>> getByName(@PathVariable String name) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByName(name);
        return Result.success(warehouses);
    }

    /**
     * 根据位置查询仓库
     * GET /api/warehouses/location/{location}
     */
    @GetMapping("/location/{location}")
    public Result<List<Warehouse>> getByLocation(@PathVariable String location) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByLocation(location);
        return Result.success(warehouses);
    }

    /**
     * 新增仓库
     * POST /api/warehouses
     */
    @PostMapping
    public Result<String> add(@RequestBody Warehouse warehouse) {
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
            return Result.error(500, "仓库添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改仓库信息
     * PUT /api/warehouses/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody Warehouse warehouse) {
        if (warehouse == null) {
            return Result.error(400, "请求体为空");
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            return Result.error(400, "仓库名称不能为空");
        }

        try {
            warehouse.setWarehouse_id(id);
            warehouseService.updateWarehouse(warehouse);
            return Result.success("仓库更新成功");
        } catch (Exception e) {
            return Result.error(500, "仓库更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除仓库
     * DELETE /api/warehouses/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            warehouseService.deleteWarehouse(id);
            return Result.success("仓库删除成功");
        } catch (Exception e) {
            return Result.error(500, "仓库删除失败：" + e.getMessage());
        }
    }
}