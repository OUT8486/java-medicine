package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.entity.PurchaseOrderItem;
import com.example.spring_boot.utils.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseOrderItemService {
    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String PURCHASE_ORDER_ITEM_CACHE_KEY = "purchase_order_item:";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增采购订单项
    public int addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        int result = purchaseOrderItemMapper.insertPurchaseOrderItem(purchaseOrderItem);
        if (result > 0) {
            // 清除相关采购订单的列表缓存
            redisUtils.delete("purchase_order:list");
        }
        return result;
    }

    // 修改采购订单项
    public void updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        purchaseOrderItemMapper.updatePurchaseOrderItem(purchaseOrderItem);
        redisUtils.delete(PURCHASE_ORDER_ITEM_CACHE_KEY + purchaseOrderItem.getPoiId());
        // 清除相关采购订单的列表缓存
        redisUtils.delete("purchase_order:list");
    }

    // 删除采购订单项
    public void deletePurchaseOrderItem(String poiId) {
        PurchaseOrderItem item = getPurchaseOrderItemById(poiId);
        purchaseOrderItemMapper.deletePurchaseOrderItem(poiId);
        redisUtils.delete(PURCHASE_ORDER_ITEM_CACHE_KEY + poiId);
        if (item != null) {
            // 清除相关采购订单的列表缓存
            redisUtils.delete("purchase_order:list");
        }
    }

    // 根据ID查询采购订单项
    public PurchaseOrderItem getPurchaseOrderItemById(String poiId) {
        String cacheKey = PURCHASE_ORDER_ITEM_CACHE_KEY + poiId;
        PurchaseOrderItem item = (PurchaseOrderItem) redisUtils.get(cacheKey);
        
        if (item != null) {
            return item;
        }
        
        item = purchaseOrderItemMapper.selectPurchaseOrderItemById(poiId);
        if (item != null) {
            redisUtils.set(cacheKey, item, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return item;
    }

    // 根据采购订单ID查询所有订单项
    public List<PurchaseOrderItem> getPurchaseOrderItemsByPoId(String poId) {
        return purchaseOrderItemMapper.selectPurchaseOrderItemsByPoId(poId);
    }

    // 根据药品ID查询采购订单项
    public List<PurchaseOrderItem> getPurchaseOrderItemsByDrugId(String drugId) {
        return purchaseOrderItemMapper.selectPurchaseOrderItemsByDrugId(drugId);
    }

    // 计算采购订单项小计
    public BigDecimal calculateSubtotal(Integer quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    // 批量添加采购订单项
    public int batchInsertPurchaseOrderItems(List<PurchaseOrderItem> items) {
        int result = purchaseOrderItemMapper.batchInsertPurchaseOrderItems(items);
        if (result > 0 && !items.isEmpty()) {
            // 清除相关采购订单的列表缓存
            redisUtils.delete("purchase_order:list");
        }
        return result;
    }

    // 根据采购订单ID删除所有订单项
    public void deletePurchaseOrderItemsByPoId(String poId) {
        purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(poId);
        // 清除相关采购订单的列表缓存
        redisUtils.delete("purchase_order:list");
    }
    public PageResult<PurchaseOrderItem> getPurchaseOrderItemsPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<PurchaseOrderItem> list = purchaseOrderItemMapper.selectPurchaseOrderItemsPage(offset, s);
        long total = purchaseOrderItemMapper.countPurchaseOrderItems();
        return new PageResult<>(list, total, p, s);
    }

}