package com.example.spring_boot.Controller;

import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.entity.PurchaseOrderItem;
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
public class PurchaseOrderItemController {

    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    // 1. 接口：获取所有采购订单项数据
    @GetMapping("/purchase-order-item/all")
    @ResponseBody
    public List<PurchaseOrderItem> getAllPurchaseOrderItems() {
        return purchaseOrderItemMapper.selectAllPurchaseOrderItems();
    }

    // 2. 接口：根据采购订单ID获取订单项
    @GetMapping("/purchase-order-item/list")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrderItem>> getPurchaseOrderItemsByPoId(
            @RequestParam String poId) {
        List<PurchaseOrderItem> items = purchaseOrderItemMapper.selectPurchaseOrderItemsByPoId(poId);
        return ResponseEntity.ok(items);
    }

    // 3. 接口：根据ID获取采购订单项
    @GetMapping("/purchase-order-item/{id}")
    @ResponseBody
    public ResponseEntity<PurchaseOrderItem> getPurchaseOrderItemById(@PathVariable String id) {
        PurchaseOrderItem item = purchaseOrderItemMapper.selectPurchaseOrderItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 接口：根据药品ID获取采购订单项
    @GetMapping("/purchase-order-item/drug/{drugId}")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrderItem>> getPurchaseOrderItemsByDrugId(@PathVariable String drugId) {
        List<PurchaseOrderItem> items = purchaseOrderItemMapper.selectPurchaseOrderItemsByDrugId(drugId);
        return ResponseEntity.ok(items);
    }

    // 5. 接口：新增采购订单项
    @PostMapping("/purchase-order-item")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addPurchaseOrderItem(@RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            // 计算采购订单项小计
            BigDecimal subtotal = purchaseOrderItem.getPrice().multiply(new BigDecimal(purchaseOrderItem.getQuantity()));
            purchaseOrderItem.setSubtotal(subtotal);
            
            int result = purchaseOrderItemMapper.insertPurchaseOrderItem(purchaseOrderItem);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "采购订单项添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "采购订单项添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 6. 接口：批量新增采购订单项
    @PostMapping("/purchase-order-item/batch")
    @ResponseBody
    public ResponseEntity<Map<String, String>> batchAddPurchaseOrderItems(@RequestBody List<PurchaseOrderItem> items) {
        try {
            // 计算每个订单项的小计
            for (PurchaseOrderItem item : items) {
                BigDecimal subtotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
                item.setSubtotal(subtotal);
            }
            
            int result = purchaseOrderItemMapper.batchInsertPurchaseOrderItems(items);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "批量添加采购订单项成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "批量添加采购订单项失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "批量添加采购订单项失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 7. 接口：更新采购订单项
    @PutMapping("/purchase-order-item/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updatePurchaseOrderItem(@PathVariable String id, @RequestBody PurchaseOrderItem purchaseOrderItem) {
        try {
            // 计算采购订单项小计
            BigDecimal subtotal = purchaseOrderItem.getPrice().multiply(new BigDecimal(purchaseOrderItem.getQuantity()));
            purchaseOrderItem.setSubtotal(subtotal);
            
            purchaseOrderItem.setPoi_id(id);
            purchaseOrderItemMapper.updatePurchaseOrderItem(purchaseOrderItem);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 9. 接口：删除采购订单项
    @DeleteMapping("/purchase-order-item/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletePurchaseOrderItem(@PathVariable String id) {
        try {
            purchaseOrderItemMapper.deletePurchaseOrderItem(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：根据采购订单ID删除所有订单项
    @DeleteMapping("/purchase-order-item/po/{poId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletePurchaseOrderItemsByPoId(@PathVariable String poId) {
        try {
            purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(poId);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "采购订单项删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}