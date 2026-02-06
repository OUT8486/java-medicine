package com.example.spring_boot.controller;

import com.example.spring_boot.dao.WarehouseInMapper;
import com.example.spring_boot.entity.WarehouseIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WarehouseInController {

    @Autowired
    private WarehouseInMapper warehouseInMapper;

    // 1. 页面跳转：访问 http://localhost:8080/warehouse-in 跳转到入库单管理页面
    @GetMapping("/warehouse-in")
    public String warehouseInPage() {
        return "warehouse-in";
    }

    // 2. 接口：获取所有入库单数据
    @GetMapping("/warehouse-in/list")
    @ResponseBody
    public List<WarehouseIn> getAllWarehouseIns() {
        return warehouseInMapper.selectAllWarehouseIns();
    }

    // 4. 接口：根据ID获取入库单
    @GetMapping("/warehouse-in/{id}")
    @ResponseBody
    public ResponseEntity<WarehouseIn> getWarehouseInById(@PathVariable String id) {
        WarehouseIn warehouseIn = warehouseInMapper.selectWarehouseInById(id);
        if (warehouseIn != null) {
            return ResponseEntity.ok(warehouseIn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 10. 接口：新增入库单
    @PostMapping("/warehouse-in")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addWarehouseIn(@RequestBody WarehouseIn warehouseIn) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 设置入库日期为当前日期
            if (warehouseIn.getIn_date() == null) {
                String dateStr = sdf.format(new Date());
                warehouseIn.setIn_date(dateStr);
            }
            
            int result = warehouseInMapper.insertWarehouseIn(warehouseIn);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "入库单添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "入库单添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "入库单添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 11. 接口：更新入库单
    @PutMapping("/warehouse-in/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateWarehouseIn(@PathVariable String id, @RequestBody WarehouseIn warehouseIn) {
        try {
            warehouseIn.setWi_id(id);
            warehouseInMapper.updateWarehouseIn(warehouseIn);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "入库单更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "入库单更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 12. 接口：删除入库单
    @DeleteMapping("/warehouse-in/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteWarehouseIn(@PathVariable String id) {
        try {
            warehouseInMapper.deleteWarehouseIn(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "入库单删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "入库单删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}