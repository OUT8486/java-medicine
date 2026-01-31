package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface PurchaseOrderMapper {
    // 新增采购订单
    int insertPurchaseOrder(PurchaseOrder purchaseOrder);
    
    // 修改采购订单
    void updatePurchaseOrder(PurchaseOrder purchaseOrder);
    
    // 删除采购订单
    void deletePurchaseOrder(String po_id);
    
    // 根据ID查询采购订单
    PurchaseOrder selectPurchaseOrderById(String po_id);
    
    // 查询所有采购订单
    List<PurchaseOrder> selectAllPurchaseOrders();
    
    // 根据供应商ID查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersBySupplierId(String supplier_id);
    
    // 根据审核状态查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersByAuditStatus(Integer audit_status);
    
    // 根据经办人查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersByEmployeeId(String employee_id);
    
    // 根据日期范围查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersByDateRange(String start_date, String end_date);
    
    // 更新审核状态
    void updatePurchaseOrderAuditStatus(String po_id, String audit_status);

    // 根据创建人查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersByCreateBy(String creator);

    // 根据日期范围查询采购订单（Date类型）
    List<PurchaseOrder> selectPurchaseOrdersByDateRange(Date startDate, Date endDate);

    // 根据状态查询采购订单
    List<PurchaseOrder> selectPurchaseOrdersByStatus(String status);

    // 计算订单总金额
    BigDecimal calculateOrderTotal(String poId);
}