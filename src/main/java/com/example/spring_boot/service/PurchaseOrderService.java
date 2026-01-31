package com.example.spring_boot.service;

import com.example.spring_boot.mapper.PurchaseOrderMapper;
import com.example.spring_boot.pojo.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    // 新增采购订单
    public int addPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
    }

    // 修改采购订单
    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    // 删除采购订单
    public void deletePurchaseOrder(String poId) {
        purchaseOrderMapper.deletePurchaseOrder(poId);
    }

    // 根据ID查询采购订单
    public PurchaseOrder getPurchaseOrderById(String poId) {
        return purchaseOrderMapper.selectPurchaseOrderById(poId);
    }

    // 查询所有采购订单
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderMapper.selectAllPurchaseOrders();
    }

    // 根据供应商ID查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersBySupplierId(String supplierId) {
        return purchaseOrderMapper.selectPurchaseOrdersBySupplierId(supplierId);
    }

    // 根据订单状态查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return purchaseOrderMapper.selectPurchaseOrdersByStatus(status);
    }

    // 根据创建人查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByCreateBy(String createBy) {
        return purchaseOrderMapper.selectPurchaseOrdersByCreateBy(createBy);
    }

    // 根据日期范围查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByDateRange(Date startDate, Date endDate) {
        return purchaseOrderMapper.selectPurchaseOrdersByDateRange(startDate, endDate);
    }

    // 更新订单状态
    public void updatePurchaseOrderStatus(String poId, String status) {
        purchaseOrderMapper.updatePurchaseOrderAuditStatus(poId, status);
    }

    // 计算订单总金额
    public BigDecimal calculateOrderTotal(String poId) {
        return purchaseOrderMapper.calculateOrderTotal(poId);
    }
}