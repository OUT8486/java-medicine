package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.SalesOrderItem;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SalesOrderItemMapper {
    // 新增销售订单项
    int insertSalesOrderItem(SalesOrderItem salesOrderItem);
    
    // 修改销售订单项
    void updateSalesOrderItem(SalesOrderItem salesOrderItem);
    
    // 删除销售订单项
    void deleteSalesOrderItem(String soi_id);
    
    // 根据ID查询销售订单项
    SalesOrderItem selectSalesOrderItemById(String soi_id);
    
    // 查询所有销售订单项
    List<SalesOrderItem> selectAllSalesOrderItems();
    
    // 根据销售订单ID查询所有订单项
    List<SalesOrderItem> selectSalesOrderItemsBySoId(String so_id);
    
    // 根据药品ID查询销售订单项
    List<SalesOrderItem> selectSalesOrderItemsByDrugId(String drug_id);
    
    // 根据批号查询销售订单项
    List<SalesOrderItem> selectSalesOrderItemsByBatchNo(String batch_no);
    
    // 批量添加销售订单项
    int batchInsertSalesOrderItems(List<SalesOrderItem> items);
    
    // 更新销售订单项小计
    void updateSubtotal(String soi_id, BigDecimal subtotal);
    
    // 根据销售订单ID删除所有订单项
    void deleteSalesOrderItemsBySoId(String so_id);
}