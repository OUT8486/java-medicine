package com.example.spring_boot.Controller;

import com.example.spring_boot.dao.WarehouseMapper;
import com.example.spring_boot.entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseMapper warehouseMapper;

    // 1. 页面跳转：访问http://localhost:8080/warehouse 跳转到仓库管理页面
    @GetMapping("/warehouse")
    public String warehousePage() {
        return "warehouse";
    }

    // 2. 接口：获取所有仓库数据
    @GetMapping("/warehouse/list")
    @ResponseBody
    public List<Warehouse> getAllWarehouses() {
        return warehouseMapper.selectAllWarehouses();
    }

    // 3. 接口：分页查询仓库数据
    @GetMapping("/warehouse/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getWarehousesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 获取仓库列表
        List<Warehouse> warehouses = warehouseMapper.selectAllWarehouses();
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("warehouses", warehouses);
        result.put("total", warehouses.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
    }

    // 4. 接口：根据ID获取仓库
    @GetMapping("/warehouse/{id}")
    @ResponseBody
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable String id) {
        Warehouse warehouse = warehouseMapper.selectWarehouseById(id);
        if (warehouse != null) {
            return ResponseEntity.ok(warehouse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. 接口：根据名称查询仓库
    @GetMapping("/warehouse/name/{name}")
    @ResponseBody
    public ResponseEntity<List<Warehouse>> getWarehousesByName(@PathVariable String name) {
        List<Warehouse> warehouses = warehouseMapper.selectWarehousesByName(name);
        return ResponseEntity.ok(warehouses);
    }

    // 6. 接口：根据位置查询仓库
    @GetMapping("/warehouse/location/{location}")
    @ResponseBody
    public ResponseEntity<List<Warehouse>> getWarehousesByLocation(@PathVariable String location) {
        List<Warehouse> warehouses = warehouseMapper.selectWarehousesByLocation(location);
        return ResponseEntity.ok(warehouses);
    }

    // 7. 接口：新增仓库
    @PostMapping("/warehouse")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addWarehouse(@RequestBody Warehouse warehouse) {
        // 基本校验
        if (warehouse == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (warehouse.getLocation() == null || warehouse.getLocation().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "location 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            int result = warehouseMapper.insertWarehouse(warehouse);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "仓库添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "仓库添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "仓库添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 8. 接口：更新仓库信息
    @PutMapping("/warehouse/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateWarehouse(@PathVariable String id, @RequestBody Warehouse warehouse) {
        // 基本校验
        if (warehouse == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (warehouse.getLocation() == null || warehouse.getLocation().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "location 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            warehouse.setWarehouse_id(id);
            warehouseMapper.updateWarehouse(warehouse);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "仓库更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "仓库更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 9. 接口：删除仓库
    @DeleteMapping("/warehouse/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteWarehouse(@PathVariable String id) {
        try {
            warehouseMapper.deleteWarehouse(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "仓库删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "仓库删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}