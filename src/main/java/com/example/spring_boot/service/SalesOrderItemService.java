package com.example.spring_boot.service;

import com.example.spring_boot.mapper.SalesOrderItemMapper;
import com.example.spring_boot.pojo.SalesOrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalesOrderItemService {
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    // 新增销售订单项
    public int addSalesOrderItem(SalesOrderItem salesOrderItem) {
        return salesOrderItemMapper.insertSalesOrderItem(salesOrderItem);
    }

    // 修改销售订单项
    public void updateSalesOrderItem(SalesOrderItem salesOrderItem) {
        salesOrderItemMapper.updateSalesOrderItem(salesOrderItem);
    }

    // 删除销售订单项
    public void deleteSalesOrderItem(String soiId) {
        salesOrderItemMapper.deleteSalesOrderItem(soiId);
    }

    // 根据ID查询销售订单项
    public SalesOrderItem getSalesOrderItemById(String soiId) {
        return salesOrderItemMapper.selectSalesOrderItemById(soiId);
    }

    // 根据销售订单ID查询所有订单项
    public List<SalesOrderItem> getSalesOrderItemsBySoId(String soId) {
        return salesOrderItemMapper.selectSalesOrderItemsBySoId(soId);
    }

    // 根据药品ID查询销售订单项
    public List<SalesOrderItem> getSalesOrderItemsByDrugId(String drugId) {
        return salesOrderItemMapper.selectSalesOrderItemsByDrugId(drugId);
    }

    // 根据批号查询销售订单项
    public List<SalesOrderItem> getSalesOrderItemsByBatchNo(String batchNo) {
        return salesOrderItemMapper.selectSalesOrderItemsByBatchNo(batchNo);
    }

    // 计算销售订单项小计
    public BigDecimal calculateSubtotal(Integer quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    // 更新销售订单项小计
    public void updateSubtotal(String soiId) {
        SalesOrderItem item = getSalesOrderItemById(soiId);
        if (item != null) {
            BigDecimal subtotal = calculateSubtotal(item.getQuantity(), item.getPrice());
            salesOrderItemMapper.updateSubtotal(soiId, subtotal);
        }
    }

    // 批量添加销售订单项
    public int batchInsertSalesOrderItems(List<SalesOrderItem> items) {
        return salesOrderItemMapper.batchInsertSalesOrderItems(items);
    }

    // 根据销售订单ID删除所有订单项
    public void deleteSalesOrderItemsBySoId(String soId) {
        salesOrderItemMapper.deleteSalesOrderItemsBySoId(soId);
    }
}
