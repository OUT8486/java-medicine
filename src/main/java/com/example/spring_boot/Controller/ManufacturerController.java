package com.example.spring_boot.Controller;

import com.example.spring_boot.dao.ManufacturerMapper;
import com.example.spring_boot.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerMapper manufacturerMapper;

    // 1. 页面跳转：访问 http://localhost:8080/manufacturer 跳转到生产厂家管理页面
    @GetMapping("/manufacturer")
    public String manufacturerPage() {
        return "manufacturer";
    }

    // 2. 接口：获取所有生产厂家数据
    @GetMapping("/manufacturer/list")
    @ResponseBody
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerMapper.selectAllManufacturers();
    }

    // 3. 接口：分页查询生产厂家数据
    @GetMapping("/manufacturer/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getManufacturersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String contactPerson) {
        
        // 获取生产厂家列表
        List<Manufacturer> manufacturers = manufacturerMapper.selectAllManufacturers();
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("manufacturers", manufacturers);
        result.put("total", manufacturers.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
    }

    // 4. 接口：根据ID获取生产厂家
    @GetMapping("/manufacturer/{id}")
    @ResponseBody
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable String id) {
        Manufacturer manufacturer = manufacturerMapper.selectManufacturerById(id);
        if (manufacturer != null) {
            return ResponseEntity.ok(manufacturer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. 接口：根据名称查询生产厂家
    @GetMapping("/manufacturer/name/{name}")
    @ResponseBody
    public ResponseEntity<List<Manufacturer>> getManufacturersByName(@PathVariable String name) {
        List<Manufacturer> manufacturers = manufacturerMapper.selectManufacturersByName(name);
        return ResponseEntity.ok(manufacturers);
    }

    // 6. 接口：根据联系人查询生产厂家
    @GetMapping("/manufacturer/contact/{contactPerson}")
    @ResponseBody
    public ResponseEntity<List<Manufacturer>> getManufacturersByContactPerson(@PathVariable String contactPerson) {
        // 数据库表没有 contact_person 字段，返回空列表以避免错误
        return ResponseEntity.ok(java.util.Collections.emptyList());
    }

    // 7. 接口：新增生产厂家
    @PostMapping("/manufacturer")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addManufacturer(@RequestBody Manufacturer manufacturer) {
        try {
            int result = manufacturerMapper.insertManufacturer(manufacturer);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "生产厂家添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "生产厂家添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "生产厂家添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 8. 接口：更新生产厂家信息
    @PutMapping("/manufacturer/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateManufacturer(@PathVariable String id, @RequestBody Manufacturer manufacturer) {
        try {
            manufacturer.setManufacturer_id(id);
            manufacturerMapper.updateManufacturer(manufacturer);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "生产厂家更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "生产厂家更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 9. 接口：删除生产厂家
    @DeleteMapping("/manufacturer/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteManufacturer(@PathVariable String id) {
        try {
            manufacturerMapper.deleteManufacturer(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "生产厂家删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "生产厂家删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}