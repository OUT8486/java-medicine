package com.example.spring_boot.service;

import com.example.spring_boot.dao.PurchaseOrderMapper;
import com.example.spring_boot.entity.PurchaseOrder;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String PURCHASE_ORDER_CACHE_KEY = "purchase_order:";
    private static final String PURCHASE_ORDER_LIST_CACHE_KEY = "purchase_order:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增采购订单
    public int addPurchaseOrder(PurchaseOrder purchaseOrder) {
        int result = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        if (result > 0) {
            redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改采购订单
    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
        redisUtils.delete(PURCHASE_ORDER_CACHE_KEY + purchaseOrder.getPo_id());
        redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
    }

    // 删除采购订单
    public void deletePurchaseOrder(String poId) {
        purchaseOrderMapper.deletePurchaseOrder(poId);
        redisUtils.delete(PURCHASE_ORDER_CACHE_KEY + poId);
        redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
    }

    // 根据ID查询采购订单
    public PurchaseOrder getPurchaseOrderById(String poId) {
        String cacheKey = PURCHASE_ORDER_CACHE_KEY + poId;
        PurchaseOrder order = (PurchaseOrder) redisUtils.get(cacheKey);
        
        if (order != null) {
            return order;
        }
        
        order = purchaseOrderMapper.selectPurchaseOrderById(poId);
        if (order != null) {
            redisUtils.set(cacheKey, order, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return order;
    }

    // 查询所有采购订单
    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> orders = (List<PurchaseOrder>) redisUtils.get(PURCHASE_ORDER_LIST_CACHE_KEY);
        
        if (orders != null) {
            return orders;
        }
        
        orders = purchaseOrderMapper.selectAllPurchaseOrders();
        if (orders != null && !orders.isEmpty()) {
            redisUtils.set(PURCHASE_ORDER_LIST_CACHE_KEY, orders, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return orders;
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
        return purchaseOrderMapper.selectPurchaseOrdersByDateRangeString(startDate, endDate);
    }

    // 更新审核状态
    public void updatePurchaseOrderAuditStatus(String poId, String auditStatus) {
        purchaseOrderMapper.updatePurchaseOrderAuditStatus(poId, auditStatus);
        redisUtils.delete(PURCHASE_ORDER_CACHE_KEY + poId);
        redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
    }
}