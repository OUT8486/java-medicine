package com.example.spring_boot.service;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.dao.SalesOrderMapper;
import com.example.spring_boot.entity.SalesOrder;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SalesOrderService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String SALES_ORDER_CACHE_KEY = "sales_order:";
    private static final String SALES_ORDER_LIST_CACHE_KEY = "sales_order:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    public int addSalesOrder(SalesOrder salesOrder) {
        int result = salesOrderMapper.insertSalesOrder(salesOrder);
        if (result > 0) {
            redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
        }
        return result;
    }

    public void updateSalesOrder(SalesOrder salesOrder) {
        salesOrderMapper.updateSalesOrder(salesOrder);
        redisUtils.delete(SALES_ORDER_CACHE_KEY + salesOrder.getSo_id());
        redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
    }

    public void deleteSalesOrder(String soId) {
        salesOrderMapper.deleteSalesOrder(soId);
        redisUtils.delete(SALES_ORDER_CACHE_KEY + soId);
        redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
    }

    /**
     * 删除销售单及其明细，整体在一个事务中执行，避免半删状态。
     */
    @Transactional
    public void deleteSalesOrderWithItems(String soId) {
        salesOrderItemMapper.deleteSalesOrderItemsBySoId(soId);
        salesOrderMapper.deleteSalesOrder(soId);
        redisUtils.delete(SALES_ORDER_CACHE_KEY + soId);
        redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
    }

    public SalesOrder getSalesOrderById(String soId) {
        String cacheKey = SALES_ORDER_CACHE_KEY + soId;
        SalesOrder order = (SalesOrder) redisUtils.get(cacheKey);
        if (order != null) {
            return order;
        }
        order = salesOrderMapper.selectSalesOrderById(soId);
        if (order != null) {
            redisUtils.set(cacheKey, order, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return order;
    }

    public List<SalesOrder> getAllSalesOrders() {
        List<SalesOrder> orders = (List<SalesOrder>) redisUtils.get(SALES_ORDER_LIST_CACHE_KEY);
        if (orders != null) {
            return orders;
        }
        orders = salesOrderMapper.selectAllSalesOrders();
        if (orders != null && !orders.isEmpty()) {
            redisUtils.set(SALES_ORDER_LIST_CACHE_KEY, orders, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return orders;
    }

    public List<SalesOrder> getSalesOrdersByCustomerId(String customerId) {
        return salesOrderMapper.selectSalesOrdersByCustomerId(customerId);
    }

    public List<SalesOrder> getSalesOrdersByEmployeeId(String employeeId) {
        return salesOrderMapper.selectSalesOrdersByEmployeeId(employeeId);
    }

    public List<SalesOrder> getSalesOrdersByDateRange(String startDate, String endDate) {
        return salesOrderMapper.selectSalesOrdersByDateRangeString(startDate, endDate);
    }
}
