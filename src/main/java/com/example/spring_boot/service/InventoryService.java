package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
import com.example.spring_boot.utils.IdGenerator;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String INVENTORY_CACHE_KEY = "inventory:";
    private static final String INVENTORY_LIST_CACHE_KEY = "inventory:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增库存记录
    public int addInventory(Inventory inventory) {
        if (inventory.getInventoryId() == null || inventory.getInventoryId().isBlank()) {
            inventory.setInventoryId(IdGenerator.next("IV"));
        }
        int result = inventoryMapper.insertInventory(inventory);
        if (result > 0) {
            redisUtils.delete(INVENTORY_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改库存信息
    public void updateInventory(Inventory inventory) {
        inventoryMapper.updateInventory(inventory);
        redisUtils.evict(INVENTORY_CACHE_KEY + inventory.getInventoryId(), INVENTORY_LIST_CACHE_KEY);
    }

    // 删除库存记录
    public void deleteInventory(String inventoryId) {
        inventoryMapper.deleteInventory(inventoryId);
        redisUtils.evict(INVENTORY_CACHE_KEY + inventoryId, INVENTORY_LIST_CACHE_KEY);
    }

    // 根据ID查询库存
    public Inventory getInventoryById(String inventoryId) {
        return redisUtils.getOrLoad(INVENTORY_CACHE_KEY + inventoryId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> inventoryMapper.selectInventoryById(inventoryId));
    }

    // 查询所有库存
    public List<Inventory> getAllInventories() {
        return redisUtils.getListOrLoad(INVENTORY_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                inventoryMapper::selectAllInventories);
    }

    public PageResult<Inventory> getInventoriesPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Inventory> list = inventoryMapper.selectInventoriesPage(offset, s);
        long total = inventoryMapper.countInventories();
        return new PageResult<>(list, total, p, s);
    }

}
