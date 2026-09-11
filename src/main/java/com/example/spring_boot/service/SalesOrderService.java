package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.dao.SalesOrderMapper;
import com.example.spring_boot.entity.SalesOrder;
import com.example.spring_boot.entity.SalesOrderItem;
import com.example.spring_boot.utils.IdGenerator;
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

    /**
     * 新增销售订单及其明细，整体在一个事务中执行；主键为空时自动生成。
     */
    @Transactional
    public int addSalesOrder(SalesOrder salesOrder) {
        if (salesOrder.getSoId() == null || salesOrder.getSoId().isBlank()) {
            salesOrder.setSoId(IdGenerator.next("SO"));
        }
        int result = salesOrderMapper.insertSalesOrder(salesOrder);
        if (result > 0 && salesOrder.getItems() != null) {
            for (SalesOrderItem item : salesOrder.getItems()) {
                if (item.getSoiId() == null || item.getSoiId().isBlank()) {
                    item.setSoiId(IdGenerator.next("SI"));
                }
                item.setSoId(salesOrder.getSoId());
                salesOrderItemMapper.insertSalesOrderItem(item);
            }
        }
        if (result > 0) {
            redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
        }
        return result;
    }

    public void updateSalesOrder(SalesOrder salesOrder) {
        salesOrderMapper.updateSalesOrder(salesOrder);
        redisUtils.delete(SALES_ORDER_CACHE_KEY + salesOrder.getSoId());
        redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
    }

    public void deleteSalesOrder(String soId) {
        salesOrderMapper.deleteSalesOrder(soId);
        redisUtils.delete(SALES_ORDER_CACHE_KEY + soId);
        redisUtils.delete(SALES_ORDER_LIST_CACHE_KEY);
    }

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
    public PageResult<SalesOrder> getSalesOrdersPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<SalesOrder> list = salesOrderMapper.selectSalesOrdersPage(offset, s);
        long total = salesOrderMapper.countSalesOrders();
        return new PageResult<>(list, total, p, s);
    }

}
