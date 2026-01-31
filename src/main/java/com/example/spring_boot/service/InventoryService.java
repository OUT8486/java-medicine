package com.example.spring_boot.service;

import com.example.spring_boot.dao.InventoryMapper;
import com.example.spring_boot.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    // 新增库存记录
    public int addInventory(Inventory inventory) {
        return inventoryMapper.insertInventory(inventory);
    }

    // 修改库存信息
    public void updateInventory(Inventory inventory) {
        inventoryMapper.updateInventory(inventory);
    }

    // 删除库存记录
    public void deleteInventory(String inventoryId) {
        inventoryMapper.deleteInventory(inventoryId);
    }

    // 根据ID查询库存
    public Inventory getInventoryById(String inventoryId) {
        return inventoryMapper.selectInventoryById(inventoryId);
    }

    // 查询所有库存
    public List<Inventory> getAllInventories() {
        return inventoryMapper.selectAllInventories();
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

    // 查询即将过期的库存
    public List<Inventory> getInventoriesNearExpiry(Date daysBeforeExpiry) {
        return inventoryMapper.selectInventoriesNearExpiry(daysBeforeExpiry);
    }

    // 查询库存不足的药品
    public List<Inventory> getLowStockInventories(int threshold) {
        return inventoryMapper.selectLowStockInventories(threshold);
    }

    // 更新库存数量
    public void updateInventoryQuantity(String inventoryId, Integer quantity) {
        inventoryMapper.updateInventoryQuantity(inventoryId, quantity);
    }
}