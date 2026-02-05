package com.example.spring_boot.service;

import com.example.spring_boot.dao.PurchaseOrderMapper;
import com.example.spring_boot.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 根据审核状态查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByAuditStatus(Integer auditStatus) {
        return purchaseOrderMapper.selectPurchaseOrdersByAuditStatus(auditStatus);
    }

    // 根据经办人查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByEmployeeId(String employeeId) {
        return purchaseOrderMapper.selectPurchaseOrdersByEmployeeId(employeeId);
    }

    // 根据日期范围查询采购订单
    public List<PurchaseOrder> getPurchaseOrdersByDateRange(String startDate, String endDate) {
        return purchaseOrderMapper.selectPurchaseOrdersByDateRange(startDate, endDate);
    }

    // 更新审核状态
    public void updatePurchaseOrderAuditStatus(String poId, String auditStatus) {
        purchaseOrderMapper.updatePurchaseOrderAuditStatus(poId, auditStatus);
    }
}