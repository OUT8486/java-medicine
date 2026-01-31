package com.example.spring_boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.pojo.SalesOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface SalesOrderMapper {
    // 新增销售订单
    int insertSalesOrder(SalesOrder salesOrder);
    
    // 修改销售订单
    void updateSalesOrder(SalesOrder salesOrder);
    
    // 删除销售订单
    void deleteSalesOrder(String so_id);
    
    // 根据ID查询销售订单
    SalesOrder selectSalesOrderById(String so_id);
    
    // 查询所有销售订单
    List<SalesOrder> selectAllSalesOrders();
    
    // 根据客户ID查询销售订单
    List<SalesOrder> selectSalesOrdersByCustomerId(String customer_id);
    
    // 根据经办人查询销售订单
    List<SalesOrder> selectSalesOrdersByEmployeeId(String employee_id);
    
    // 根据状态查询销售订单
    List<SalesOrder> selectSalesOrdersByStatus(Integer audit_status);
    
    // 根据日期范围查询销售订单
    List<SalesOrder> selectSalesOrdersByDateRange(String start_date, String end_date);

    // 根据创建人查询销售订单
    List<SalesOrder> selectSalesOrdersByCreateBy(String createBy);

    // 根据日期范围查询销售订单（Date类型）
    List<SalesOrder> selectSalesOrdersByDateRange(Date startDate, Date endDate);

    // 计算订单总金额
    BigDecimal calculateOrderTotal(String so_id);
}