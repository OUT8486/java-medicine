package com.example.spring_boot.controller;
import com.example.spring_boot.entity.PageResult;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.WarehouseIn;
import com.example.spring_boot.service.WarehouseInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 入库单管理控制器
 */
@RestController
@RequestMapping("/api/warehouse-in")
public class WarehouseInController {

    private static final Logger log = LoggerFactory.getLogger(WarehouseInController.class);

    @Autowired
    private WarehouseInService warehouseInService;

    /**
     * 获取所有入库单列表
     * GET /api/warehouse-in
     */
    @GetMapping
    public Result<List<WarehouseIn>> list() {
        List<WarehouseIn> warehouseIns = warehouseInService.getAllWarehouseIns();
        return Result.success(warehouseIns);
    }

    /**
     * 根据 ID 获取入库单详情
     * GET /api/warehouse-in/{id}
     */
    @GetMapping("/{id}")
    public Result<WarehouseIn> getById(@PathVariable String id) {
        WarehouseIn warehouseIn = warehouseInService.getWarehouseInById(id);
        if (warehouseIn != null) {
            return Result.success(warehouseIn);
        } else {
            return Result.error(404, "入库单不存在");
        }
    }

    /**
     * 新增入库单
     * POST /api/warehouse-in
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody WarehouseIn warehouseIn) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 设置入库日期为当前日期
            if (warehouseIn.getInDate() == null) {
                warehouseIn.setInDate(sdf.format(new Date()));
            }
            
            int result = warehouseInService.addWarehouseIn(warehouseIn);
            if (result > 0) {
                return Result.success("入库单添加成功");
            } else {
                return Result.error("入库单添加失败");
            }
        } catch (Exception e) {
            log.error("入库单添加失败", e);
            return Result.error(500, "入库单添加失败，请稍后重试");
        }
    }

    /**
     * 修改入库单信息
     * PUT /api/warehouse-in/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody WarehouseIn warehouseIn) {
        try {
            warehouseIn.setWiId(id);
            warehouseInService.updateWarehouseIn(warehouseIn);
            return Result.success("入库单更新成功");
        } catch (Exception e) {
            log.error("入库单更新失败", e);
            return Result.error(500, "入库单更新失败，请稍后重试");
        }
    }

    /**
     * 删除入库单
     * DELETE /api/warehouse-in/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            warehouseInService.deleteWarehouseIn(id);
            return Result.success("入库单删除成功");
        } catch (Exception e) {
            log.error("入库单删除失败", e);
            return Result.error(500, "入库单删除失败，请稍后重试");
        }
    }
    @GetMapping("/page")
    public Result<PageResult<WarehouseIn>> page(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return Result.success(warehouseInService.getWarehouseInsPage(page, size));
    }

}