package com.example.spring_boot.service;

import com.example.spring_boot.dao.SalesOrderMapper;
import com.example.spring_boot.entity.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    // 新增销售订单
    public int addSalesOrder(SalesOrder salesOrder) {
        return salesOrderMapper.insertSalesOrder(salesOrder);
    }

    // 修改销售订单
    public void updateSalesOrder(SalesOrder salesOrder) {
        salesOrderMapper.updateSalesOrder(salesOrder);
    }

    // 删除销售订单
    public void deleteSalesOrder(String soId) {
        salesOrderMapper.deleteSalesOrder(soId);
    }

    // 根据ID查询销售订单
    public SalesOrder getSalesOrderById(String soId) {
        return salesOrderMapper.selectSalesOrderById(soId);
    }

    // 查询所有销售订单
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderMapper.selectAllSalesOrders();
    }

    // 根据客户ID查询销售订单
    public List<SalesOrder> getSalesOrdersByCustomerId(String customerId) {
        return salesOrderMapper.selectSalesOrdersByCustomerId(customerId);
    }

    // 根据经办人查询销售订单
    public List<SalesOrder> getSalesOrdersByEmployeeId(String employeeId) {
        return salesOrderMapper.selectSalesOrdersByEmployeeId(employeeId);
    }

    // 根据日期范围查询销售订单
    public List<SalesOrder> getSalesOrdersByDateRange(String startDate, String endDate) {
        return salesOrderMapper.selectSalesOrdersByDateRangeString(startDate, endDate);
    }
}