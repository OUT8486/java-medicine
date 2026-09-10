package com.example.spring_boot.controller;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.DrugWithManufacturer;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 药品管理控制器
 * 提供药品的 CRUD 接口
 */
@RestController
@RequestMapping("/api/drugs")
public class DrugController {

    private static final Logger log = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    private DrugService drugService;

    /**
     * 获取所有药品列表
     * GET /api/drugs
     */
    @GetMapping
    public Result<List<Drug>> list() {
        List<Drug> drugs = drugService.getAllDrugs();
        return Result.success(drugs);
    }

    /**
     * 根据 ID 获取药品详情
     * GET /api/drugs/{id}
     */
    @GetMapping("/{id}")
    public Result<Drug> getById(@PathVariable String id) {
        Drug drug = drugService.getDrugById(id);
        if (drug != null) {
            return Result.success(drug);
        } else {
            return Result.error(404, "药品不存在");
        }
    }

    /**
     * 获取药品及其生产厂家信息
     * GET /api/drugs/{id}/manufacturer
     */
    @GetMapping("/{id}/manufacturer")
    public Result<DrugWithManufacturer> getWithManufacturer(@PathVariable String id) {
        DrugWithManufacturer drug = drugService.getDrugWithManufacturer(id);
        if (drug != null) {
            return Result.success(drug);
        } else {
            return Result.error(404, "药品信息不存在");
        }
    }

    /**
     * 新增药品
     * POST /api/drugs
     * Body: { "generic_name": "感冒灵", "retail_price": 25.5, ... }
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@RequestBody Drug drug) {
        // 基本输入校验
        if (drug == null) {
            return Result.error(400, "请求体为空");
        }
        if (drug.getGeneric_name() == null || drug.getGeneric_name().trim().isEmpty()) {
            return Result.error(400, "药品名称不能为空");
        }
        BigDecimal retail = drug.getRetail_price();
        if (retail == null || retail.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "零售价格必须大于 0");
        }
        BigDecimal purchase = drug.getPurchase_price();
        if (purchase != null && purchase.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "采购价格必须大于 0 或为空");
        }

        try {
            int result = drugService.addDrug(drug);
            if (result > 0) {
                return Result.success("药品添加成功");
            } else {
                return Result.error("药品添加失败");
            }
        } catch (Exception e) {
            log.error("药品添加失败", e);
            return Result.error(500, "药品添加失败，请稍后重试");
        }
    }

    /**
     * 修改药品信息
     * PUT /api/drugs/{id}
     * Body: { "drug_id": "1", "generic_name": "感冒灵", ... }
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody Drug drug) {
        if (drug == null) {
            return Result.error(400, "请求体为空");
        }
        
        BigDecimal retail = drug.getRetail_price();
        if (retail != null && retail.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "零售价格必须大于 0");
        }
        BigDecimal purchase = drug.getPurchase_price();
        if (purchase != null && purchase.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "采购价格必须大于 0 或为空");
        }

        try {
            drug.setDrug_id(id);
            drugService.updateDrug(drug);
            return Result.success("药品更新成功");
        } catch (Exception e) {
            log.error("药品更新失败", e);
            return Result.error(500, "药品更新失败，请稍后重试");
        }
    }

    /**
     * 删除药品
     * DELETE /api/drugs/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            drugService.deleteDrug(id);
            return Result.success("药品删除成功");
        } catch (Exception e) {
            log.error("药品删除失败", e);
            return Result.error(500, "药品删除失败，请稍后重试");
        }
    }
}