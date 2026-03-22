package com.example.spring_boot.controller;

import com.example.spring_boot.dao.WarehouseInMapper;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.WarehouseIn;
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
@CrossOrigin(origins = "*")
public class WarehouseInController {

    @Autowired
    private WarehouseInMapper warehouseInMapper;

    /**
     * 获取所有入库单列表
     * GET /api/warehouse-in
     */
    @GetMapping
    public Result<List<WarehouseIn>> list() {
        List<WarehouseIn> warehouseIns = warehouseInMapper.selectAllWarehouseIns();
        return Result.success(warehouseIns);
    }

    /**
     * 根据 ID 获取入库单详情
     * GET /api/warehouse-in/{id}
     */
    @GetMapping("/{id}")
    public Result<WarehouseIn> getById(@PathVariable String id) {
        WarehouseIn warehouseIn = warehouseInMapper.selectWarehouseInById(id);
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
    @PostMapping
    public Result<String> add(@RequestBody WarehouseIn warehouseIn) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 设置入库日期为当前日期
            if (warehouseIn.getIn_date() == null) {
                warehouseIn.setIn_date(sdf.format(new Date()));
            }
            
            int result = warehouseInMapper.insertWarehouseIn(warehouseIn);
            if (result > 0) {
                return Result.success("入库单添加成功");
            } else {
                return Result.error("入库单添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "入库单添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改入库单信息
     * PUT /api/warehouse-in/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody WarehouseIn warehouseIn) {
        try {
            warehouseIn.setWi_id(id);
            int result = warehouseInMapper.updateWarehouseIn(warehouseIn);
            if (result > 0) {
                return Result.success("入库单更新成功");
            } else {
                return Result.error("入库单更新失败");
            }
        } catch (Exception e) {
            return Result.error(500, "入库单更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除入库单
     * DELETE /api/warehouse-in/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            int result = warehouseInMapper.deleteWarehouseIn(id);
            if (result > 0) {
                return Result.success("入库单删除成功");
            } else {
                return Result.error("入库单删除失败");
            }
        } catch (Exception e) {
            return Result.error(500, "入库单删除失败：" + e.getMessage());
        }
    }
}