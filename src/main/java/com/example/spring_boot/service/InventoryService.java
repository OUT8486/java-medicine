package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
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
        int result = inventoryMapper.insertInventory(inventory);
        if (result > 0) {
            redisUtils.delete(INVENTORY_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改库存信息
    public void updateInventory(Inventory inventory) {
        inventoryMapper.updateInventory(inventory);
        redisUtils.delete(INVENTORY_CACHE_KEY + inventory.getInventoryId());
        redisUtils.delete(INVENTORY_LIST_CACHE_KEY);
    }

    // 删除库存记录
    public void deleteInventory(String inventoryId) {
        inventoryMapper.deleteInventory(inventoryId);
        redisUtils.delete(INVENTORY_CACHE_KEY + inventoryId);
        redisUtils.delete(INVENTORY_LIST_CACHE_KEY);
    }

    // 根据ID查询库存
    public Inventory getInventoryById(String inventoryId) {
        String cacheKey = INVENTORY_CACHE_KEY + inventoryId;
        Inventory inventory = (Inventory) redisUtils.get(cacheKey);
        
        if (inventory != null) {
            return inventory;
        }
        
        inventory = inventoryMapper.selectInventoryById(inventoryId);
        if (inventory != null) {
            redisUtils.set(cacheKey, inventory, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return inventory;
    }

    // 查询所有库存
    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = (List<Inventory>) redisUtils.get(INVENTORY_LIST_CACHE_KEY);
        
        if (inventories != null) {
            return inventories;
        }
        
        inventories = inventoryMapper.selectAllInventories();
        if (inventories != null && !inventories.isEmpty()) {
            redisUtils.set(INVENTORY_LIST_CACHE_KEY, inventories, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return inventories;
    }

    // 根据药品ID查询库存
    public List<Inventory> getInventoriesByDrugId(String drugId) {
        return inventoryMapper.selectInventoriesByDrugId(drugId);
    }

    // 根据仓库ID查询库存
    public List<Inventory> getInventoriesByWarehouseId(String warehouseId) {
        return inventoryMapper.selectInventoriesByWarehouseId(warehouseId);
    }

    // 根据批号查询库存
    public List<Inventory> getInventoriesByBatchNo(String batchNo) {
        return inventoryMapper.selectInventoriesByBatchNo(batchNo);
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