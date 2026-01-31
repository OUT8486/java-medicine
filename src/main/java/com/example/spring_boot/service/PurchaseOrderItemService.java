package com.example.spring_boot.service;

import com.example.spring_boot.mapper.PurchaseOrderItemMapper;
import com.example.spring_boot.pojo.PurchaseOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseOrderItemService {
    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    // 新增采购订单项
    public int addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        return purchaseOrderItemMapper.insertPurchaseOrderItem(purchaseOrderItem);
    }

    // 修改采购订单项
    public void updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        purchaseOrderItemMapper.updatePurchaseOrderItem(purchaseOrderItem);
    }

    // 删除采购订单项
    public void deletePurchaseOrderItem(String poiId) {
        purchaseOrderItemMapper.deletePurchaseOrderItem(poiId);
    }

    // 根据ID查询采购订单项
    public PurchaseOrderItem getPurchaseOrderItemById(String poiId) {
        return purchaseOrderItemMapper.selectPurchaseOrderItemById(poiId);
    }

    // 根据采购订单ID查询所有订单项
    public List<PurchaseOrderItem> getPurchaseOrderItemsByPoId(String poId) {
        return purchaseOrderItemMapper.selectPurchaseOrderItemsByPoId(poId);
    }

    // 根据药品ID查询采购订单项
    public List<PurchaseOrderItem> getPurchaseOrderItemsByDrugId(String drugId) {
        return purchaseOrderItemMapper.selectPurchaseOrderItemsByDrugId(drugId);
    }

    // 计算采购订单项小计
    public BigDecimal calculateSubtotal(Integer quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    // 更新采购订单项小计
    public void updateSubtotal(String poiId) {
        PurchaseOrderItem item = getPurchaseOrderItemById(poiId);
        if (item != null) {
            BigDecimal subtotal = calculateSubtotal(item.getQuantity(), item.getPrice());
            purchaseOrderItemMapper.updateSubtotal(poiId, subtotal);
        }
    }

    // 批量添加采购订单项
    public int batchInsertPurchaseOrderItems(List<PurchaseOrderItem> items) {
        return purchaseOrderItemMapper.batchInsertPurchaseOrderItems(items);
    }

    // 根据采购订单ID删除所有订单项
    public void deletePurchaseOrderItemsByPoId(String poId) {
        purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(poId);
    }
}