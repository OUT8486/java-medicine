package com.example.spring_boot.Controller;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.entity.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SalesOrderItemController {

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    // 1. 接口：获取所有销售订单项数据
    @GetMapping("/sales-order-item/all")
    @ResponseBody
    public List<SalesOrderItem> getAllSalesOrderItems() {
        return salesOrderItemMapper.selectAllSalesOrderItems();
    }

    // 2. 接口：根据销售订单ID获取订单项
    @GetMapping("/sales-order-item/list")
    @ResponseBody
    public ResponseEntity<List<SalesOrderItem>> getSalesOrderItemsBySoId(
            @RequestParam String soId) {
        List<SalesOrderItem> items = salesOrderItemMapper.selectSalesOrderItemsBySoId(soId);
        return ResponseEntity.ok(items);
    }

    // 3. 接口：根据ID获取销售订单项
    @GetMapping("/sales-order-item/{id}")
    @ResponseBody
    public ResponseEntity<SalesOrderItem> getSalesOrderItemById(@PathVariable String id) {
        SalesOrderItem item = salesOrderItemMapper.selectSalesOrderItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 接口：根据药品ID获取销售订单项
    @GetMapping("/sales-order-item/drug/{drugId}")
    @ResponseBody
    public ResponseEntity<List<SalesOrderItem>> getSalesOrderItemsByDrugId(@PathVariable String drugId) {
        List<SalesOrderItem> items = salesOrderItemMapper.selectSalesOrderItemsByDrugId(drugId);
        return ResponseEntity.ok(items);
    }

    // 5. 接口：根据批号获取销售订单项
    @GetMapping("/sales-order-item/batch/{batchNo}")
    @ResponseBody
    public ResponseEntity<List<SalesOrderItem>> getSalesOrderItemsByBatchNo(@PathVariable String batchNo) {
        List<SalesOrderItem> items = salesOrderItemMapper.selectSalesOrderItemsByBatchNo(batchNo);
        return ResponseEntity.ok(items);
    }

    // 6. 接口：新增销售订单项
    @PostMapping("/sales-order-item")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSalesOrderItem(@RequestBody SalesOrderItem salesOrderItem) {
        try {
            // 计算销售订单项小计
            BigDecimal subtotal = salesOrderItem.getPrice().multiply(new BigDecimal(salesOrderItem.getQuantity()));
            salesOrderItem.setSubtotal(subtotal);
            
            int result = salesOrderItemMapper.insertSalesOrderItem(salesOrderItem);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "销售订单项添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "销售订单项添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 7. 接口：批量新增销售订单项
    @PostMapping("/sales-order-item/batch")
    @ResponseBody
    public ResponseEntity<Map<String, String>> batchAddSalesOrderItems(@RequestBody List<SalesOrderItem> items) {
        try {
            // 计算每个订单项的小计
            for (SalesOrderItem item : items) {
                BigDecimal subtotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
                item.setSubtotal(subtotal);
            }
            
            int result = salesOrderItemMapper.batchInsertSalesOrderItems(items);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "批量添加销售订单项成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "批量添加销售订单项失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "批量添加销售订单项失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 8. 接口：更新销售订单项
    @PutMapping("/sales-order-item/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateSalesOrderItem(@PathVariable String id, @RequestBody SalesOrderItem salesOrderItem) {
        try {
            // 计算销售订单项小计
            BigDecimal subtotal = salesOrderItem.getPrice().multiply(new BigDecimal(salesOrderItem.getQuantity()));
            salesOrderItem.setSubtotal(subtotal);
            
            salesOrderItem.setSoi_id(id);
            salesOrderItemMapper.updateSalesOrderItem(salesOrderItem);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：删除销售订单项
    @DeleteMapping("/sales-order-item/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSalesOrderItem(@PathVariable String id) {
        try {
            salesOrderItemMapper.deleteSalesOrderItem(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 11. 接口：根据销售订单ID删除所有订单项
    @DeleteMapping("/sales-order-item/so/{soId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSalesOrderItemsBySoId(@PathVariable String soId) {
        try {
            salesOrderItemMapper.deleteSalesOrderItemsBySoId(soId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "销售订单项删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}