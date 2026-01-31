package com.example.spring_boot.Controller;

import com.example.spring_boot.mapper.WarehouseInMapper;
import com.example.spring_boot.pojo.WarehouseIn;
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

    // 3. 接口：分页查询入库单数据
    @GetMapping("/warehouse-in/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getWarehouseInsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String poId,
            @RequestParam(required = false) String warehouseId,
            @RequestParam(required = false) String batchNo) {
        
        // 获取入库单列表
        List<WarehouseIn> warehouseIns = warehouseInMapper.selectAllWarehouseIns();
        
        // 应用过滤条件
        if (search != null && !search.trim().isEmpty()) {
            // 这里可以根据需要添加搜索逻辑
        }
        if (poId != null && !poId.trim().isEmpty()) {
            warehouseIns = warehouseInMapper.selectWarehouseInsByPoId(poId);
        }
        if (warehouseId != null && !warehouseId.trim().isEmpty()) {
            warehouseIns = warehouseInMapper.selectWarehouseInsByWarehouseId(warehouseId);
        }
        if (batchNo != null && !batchNo.trim().isEmpty()) {
            warehouseIns = warehouseInMapper.selectWarehouseInsByBatchNo(batchNo);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("warehouseIns", warehouseIns);
        response.put("total", warehouseIns.size());
        response.put("page", page);
        response.put("size", size);
        
        return ResponseEntity.ok(response);
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

    // 5. 接口：根据采购订单ID查询入库单
    @GetMapping("/warehouse-in/po/{poId}")
    @ResponseBody
    public ResponseEntity<List<WarehouseIn>> getWarehouseInsByPoId(@PathVariable String poId) {
        List<WarehouseIn> warehouseIns = warehouseInMapper.selectWarehouseInsByPoId(poId);
        return ResponseEntity.ok(warehouseIns);
    }

    // 6. 接口：根据仓库ID查询入库单
    @GetMapping("/warehouse-in/warehouse/{warehouseId}")
    @ResponseBody
    public ResponseEntity<List<WarehouseIn>> getWarehouseInsByWarehouseId(@PathVariable String warehouseId) {
        List<WarehouseIn> warehouseIns = warehouseInMapper.selectWarehouseInsByWarehouseId(warehouseId);
        return ResponseEntity.ok(warehouseIns);
    }

    // 9. 接口：根据批号查询入库单
    @GetMapping("/warehouse-in/batch/{batchNo}")
    @ResponseBody
    public ResponseEntity<List<WarehouseIn>> getWarehouseInsByBatchNo(@PathVariable String batchNo) {
        List<WarehouseIn> warehouseIns = warehouseInMapper.selectWarehouseInsByBatchNo(batchNo);
        return ResponseEntity.ok(warehouseIns);
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