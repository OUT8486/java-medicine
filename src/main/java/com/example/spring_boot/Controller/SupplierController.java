package com.example.spring_boot.controller;

import com.example.spring_boot.dao.SupplierMapper;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商管理控制器
 */
@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 获取所有供应商列表
     * GET /api/suppliers
     */
    @GetMapping
    public Result<List<Supplier>> list() {
        List<Supplier> suppliers = supplierMapper.selectAllSuppliers();
        return Result.success(suppliers);
    }

    /**
     * 根据 ID 获取供应商详情
     * GET /api/suppliers/{id}
     */
    @GetMapping("/{id}")
    public Result<Supplier> getById(@PathVariable String id) {
        Supplier supplier = supplierMapper.selectSupplierById(id);
        if (supplier != null) {
            return Result.success(supplier);
        } else {
            return Result.error(404, "供应商不存在");
        }
    }

    /**
     * 新增供应商
     * POST /api/suppliers
     */
    @PostMapping
    public Result<String> add(@RequestBody Supplier supplier) {
        if (supplier == null) {
            return Result.error(400, "请求体为空");
        }
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            return Result.error(400, "供应商名称不能为空");
        }
        String phone = supplier.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            return Result.error(400, "联系电话必须为 11 位数字");
        }
        Integer status = supplier.getStatus();
        if (status != null && status != 0 && status != 1) {
            return Result.error(400, "状态必须为 0 或 1");
        }

        try {
            int result = supplierMapper.insertSupplier(supplier);
            if (result > 0) {
                return Result.success("供应商添加成功");
            } else {
                return Result.error("供应商添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "供应商添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改供应商信息
     * PUT /api/suppliers/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody Supplier supplier) {
        if (supplier == null) {
            return Result.error(400, "请求体为空");
        }
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            return Result.error(400, "供应商名称不能为空");
        }
        String phone = supplier.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            return Result.error(400, "联系电话必须为 11 位数字");
        }
        Integer status = supplier.getStatus();
        if (status != null && status != 0 && status != 1) {
            return Result.error(400, "状态必须为 0 或 1");
        }

        try {
            supplier.setSupplier_id(id);
            int result = supplierMapper.updateSupplier(supplier);
            if (result > 0) {
                return Result.success("供应商更新成功");
            } else {
                return Result.error("供应商更新失败");
            }
        } catch (Exception e) {
            return Result.error(500, "供应商更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除供应商
     * DELETE /api/suppliers/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            int result = supplierMapper.deleteSupplier(id);
            if (result > 0) {
                return Result.success("供应商删除成功");
            } else {
                return Result.error("供应商删除失败");
            }
        } catch (Exception e) {
            return Result.error(500, "供应商删除失败：" + e.getMessage());
        }
    }
}