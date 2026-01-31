package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.PurchaseOrderItem;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PurchaseOrderItemMapper {
    // 新增采购订单项
    int insertPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
    
    // 修改采购订单项
    void updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
    
    // 删除采购订单项
    void deletePurchaseOrderItem(String poi_id);
    
    // 根据ID查询采购订单项
    PurchaseOrderItem selectPurchaseOrderItemById(String poi_id);
    
    // 查询所有采购订单项
    List<PurchaseOrderItem> selectAllPurchaseOrderItems();
    
    // 根据采购订单ID查询所有订单项
    List<PurchaseOrderItem> selectPurchaseOrderItemsByPoId(String po_id);
    
    // 根据药品ID查询采购订单项
    List<PurchaseOrderItem> selectPurchaseOrderItemsByDrugId(String drug_id);
    
    // 更新采购订单项小计
    void updateSubtotal(String poi_id, BigDecimal subtotal);
    
    // 批量添加采购订单项
    int batchInsertPurchaseOrderItems(List<PurchaseOrderItem> items);
    
    // 根据采购订单ID删除所有订单项
    void deletePurchaseOrderItemsByPoId(String po_id);
}