package com.example.spring_boot.service;

import com.example.spring_boot.dao.SalesOrderItemMapper;
import com.example.spring_boot.entity.SalesOrderItem;
import com.example.spring_boot.utils.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SalesOrderItemService {
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String SALES_ORDER_ITEM_CACHE_KEY = "sales_order_item:";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增销售订单项
    public int addSalesOrderItem(SalesOrderItem salesOrderItem) {
        int result = salesOrderItemMapper.insertSalesOrderItem(salesOrderItem);
        if (result > 0) {
            // 清除相关销售订单的列表缓存
            redisUtils.delete("sales_order:list");
        }
        return result;
    }

    // 修改销售订单项
    public void updateSalesOrderItem(SalesOrderItem salesOrderItem) {
        salesOrderItemMapper.updateSalesOrderItem(salesOrderItem);
        redisUtils.delete(SALES_ORDER_ITEM_CACHE_KEY + salesOrderItem.getSoi_id());
        // 清除相关销售订单的列表缓存
        redisUtils.delete("sales_order:list");
    }

    // 删除销售订单项
    public void deleteSalesOrderItem(String soiId) {
        SalesOrderItem item = getSalesOrderItemById(soiId);
        salesOrderItemMapper.deleteSalesOrderItem(soiId);
        redisUtils.delete(SALES_ORDER_ITEM_CACHE_KEY + soiId);
        if (item != null) {
            // 清除相关销售订单的列表缓存
            redisUtils.delete("sales_order:list");
        }
    }

    // 根据ID查询销售订单项
    public SalesOrderItem getSalesOrderItemById(String soiId) {
        String cacheKey = SALES_ORDER_ITEM_CACHE_KEY + soiId;
        SalesOrderItem item = (SalesOrderItem) redisUtils.get(cacheKey);
        
        if (item != null) {
            return item;
        }
        
        item = salesOrderItemMapper.selectSalesOrderItemById(soiId);
        if (item != null) {
            redisUtils.set(cacheKey, item, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return item;
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
            redisUtils.delete(SALES_ORDER_ITEM_CACHE_KEY + soiId);
        }
    }

    // 批量添加销售订单项
    public int batchInsertSalesOrderItems(List<SalesOrderItem> items) {
        int result = salesOrderItemMapper.batchInsertSalesOrderItems(items);
        if (result > 0 && !items.isEmpty()) {
            // 清除相关销售订单的列表缓存
            redisUtils.delete("sales_order:list");
        }
        return result;
    }

    // 根据销售订单ID删除所有订单项
    public void deleteSalesOrderItemsBySoId(String soId) {
        salesOrderItemMapper.deleteSalesOrderItemsBySoId(soId);
        // 清除相关销售订单的列表缓存
        redisUtils.delete("sales_order:list");
    }
}