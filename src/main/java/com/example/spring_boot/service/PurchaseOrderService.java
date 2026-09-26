package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.PurchaseOrderItemMapper;
import com.example.spring_boot.dao.PurchaseOrderMapper;
import com.example.spring_boot.entity.PurchaseOrder;
import com.example.spring_boot.entity.PurchaseOrderItem;
import com.example.spring_boot.utils.IdGenerator;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String PURCHASE_ORDER_CACHE_KEY = "purchase_order:";
    private static final String PURCHASE_ORDER_LIST_CACHE_KEY = "purchase_order:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    /**
     * 新增采购订单及其明细，整体在一个事务中执行；主键为空时自动生成。
     */
    @Transactional
    public int addPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getPoId() == null || purchaseOrder.getPoId().isBlank()) {
            purchaseOrder.setPoId(IdGenerator.next("PO"));
        }
        int result = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        if (result > 0 && purchaseOrder.getItems() != null) {
            for (PurchaseOrderItem item : purchaseOrder.getItems()) {
                if (item.getPoiId() == null || item.getPoiId().isBlank()) {
                    item.setPoiId(IdGenerator.next("PI"));
                }
                item.setPoId(purchaseOrder.getPoId());
                purchaseOrderItemMapper.insertPurchaseOrderItem(item);
            }
        }
        if (result > 0) {
            redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
        }
        return result;
    }

    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
        redisUtils.evict(PURCHASE_ORDER_CACHE_KEY + purchaseOrder.getPoId(), PURCHASE_ORDER_LIST_CACHE_KEY);
    }


    @Transactional
    public void deletePurchaseOrderWithItems(String poId) {
        purchaseOrderItemMapper.deletePurchaseOrderItemsByPoId(poId);
        purchaseOrderMapper.deletePurchaseOrder(poId);
        redisUtils.delete(PURCHASE_ORDER_CACHE_KEY + poId);
        redisUtils.delete(PURCHASE_ORDER_LIST_CACHE_KEY);
    }

    public PurchaseOrder getPurchaseOrderById(String poId) {
        return redisUtils.getOrLoad(PURCHASE_ORDER_CACHE_KEY + poId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> purchaseOrderMapper.selectPurchaseOrderById(poId));
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return redisUtils.getListOrLoad(PURCHASE_ORDER_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                purchaseOrderMapper::selectAllPurchaseOrders);
    }


    public PageResult<PurchaseOrder> getPurchaseOrdersPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<PurchaseOrder> list = purchaseOrderMapper.selectPurchaseOrdersPage(offset, s);
        long total = purchaseOrderMapper.countPurchaseOrders();
        return new PageResult<>(list, total, p, s);
    }

}
