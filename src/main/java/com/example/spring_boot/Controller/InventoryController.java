package com.example.spring_boot.Controller;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InventoryController {

    @Autowired
    private InventoryMapper inventoryMapper;

    // 1. 页面跳转：访问 http://localhost:8080/inventory 跳转到库存管理页面
    @GetMapping("/inventory")
    public String inventoryPage() {
        return "inventory";
    }

    // 2. 接口：获取所有库存数据
    @GetMapping("/inventory/list")
    @ResponseBody
    public List<Inventory> getAllInventories() {
        return inventoryMapper.selectAllInventories();
    }

    // 3. 接口：分页查询库存数据
    @GetMapping("/inventory/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getInventoriesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String warehouseId,
            @RequestParam(required = false) String batchNo) {
        
        // 获取库存列表
        List<Inventory> inventories = inventoryMapper.selectAllInventories();
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("inventories", inventories);
        result.put("total", inventories.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
    }

    // 4. 接口：根据ID获取库存
    @GetMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Inventory inventory = inventoryMapper.selectInventoryById(id);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. 接口：根据药品ID查询库存
    @GetMapping("/inventory/drug/{drugId}")
    @ResponseBody
    public ResponseEntity<List<Inventory>> getInventoriesByDrugId(@PathVariable String drugId) {
        List<Inventory> inventories = inventoryMapper.selectInventoriesByDrugId(drugId);
        return ResponseEntity.ok(inventories);
    }

    // 6. 接口：根据仓库ID查询库存
    @GetMapping("/inventory/warehouse/{warehouseId}")
    @ResponseBody
    public ResponseEntity<List<Inventory>> getInventoriesByWarehouseId(@PathVariable String warehouseId) {
        List<Inventory> inventories = inventoryMapper.selectInventoriesByWarehouseId(warehouseId);
        return ResponseEntity.ok(inventories);
    }

    // 7. 接口：根据批号查询库存
    @GetMapping("/inventory/batch/{batchNo}")
    @ResponseBody
    public ResponseEntity<List<Inventory>> getInventoriesByBatchNo(@PathVariable String batchNo) {
        List<Inventory> inventories = inventoryMapper.selectInventoriesByBatchNo(batchNo);
        return ResponseEntity.ok(inventories);
    }

    // 8. 接口：查询即将过期的库存
    @GetMapping("/inventory/near-expiry")
    @ResponseBody
    public ResponseEntity<List<Inventory>> getInventoriesNearExpiry(@RequestParam(defaultValue = "30") int days) {
        Date expiryDate = new Date(System.currentTimeMillis() + (long) days * 24 * 60 * 60 * 1000);
        List<Inventory> inventories = inventoryMapper.selectInventoriesNearExpiry(expiryDate);
        return ResponseEntity.ok(inventories);
    }

    // 9. 接口：查询库存不足的药品
    @GetMapping("/inventory/low-stock")
    @ResponseBody
    public ResponseEntity<List<Inventory>> getLowStockInventories(@RequestParam(defaultValue = "10") int threshold) {
        List<Inventory> inventories = inventoryMapper.selectLowStockInventories(threshold);
        return ResponseEntity.ok(inventories);
    }

    // 10. 接口：获取库存统计数据
    @GetMapping("/inventory/statistics")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getInventoryStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // 获取所有库存
        List<Inventory> allInventories = inventoryMapper.selectAllInventories();
        
        // 计算统计数据
        int totalTypes = allInventories.size();
        
        // 获取库存不足的药品数量
        List<Inventory> lowStockInventories = inventoryMapper.selectLowStockInventories(10);
        int lowStockCount = lowStockInventories.size();
        
        // 获取即将过期的药品数量
        Date thirtyDaysLater = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
        List<Inventory> nearExpiryInventories = inventoryMapper.selectInventoriesNearExpiry(thirtyDaysLater);
        int nearExpiryCount = nearExpiryInventories.size();
        
        // 正常库存数量
        int normalStockCount = totalTypes - lowStockCount;
        
        stats.put("totalTypes", totalTypes);
        stats.put("lowStock", lowStockCount);
        stats.put("nearExpiry", nearExpiryCount);
        stats.put("normalStock", normalStockCount);
        
        return ResponseEntity.ok(stats);
    }

    // 11. 接口：新增库存
    @PostMapping("/inventory")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addInventory(@RequestBody Inventory inventory) {
        try {
            int result = inventoryMapper.insertInventory(inventory);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "库存添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "库存添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 12. 接口：更新库存信息
    @PutMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateInventory(@PathVariable String id, @RequestBody Inventory inventory) {
        try {
            inventory.setInventory_id(id);
            inventoryMapper.updateInventory(inventory);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 13. 接口：更新库存数量
    @PutMapping("/inventory/{id}/quantity")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateInventoryQuantity(
            @PathVariable String id, 
            @RequestParam Integer quantity) {
        try {
            inventoryMapper.updateInventoryQuantity(id, quantity);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存数量更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存数量更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 14. 接口：删除库存
    @DeleteMapping("/inventory/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteInventory(@PathVariable String id) {
        try {
            inventoryMapper.deleteInventory(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "库存删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}