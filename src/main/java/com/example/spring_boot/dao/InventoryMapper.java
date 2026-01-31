package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Inventory;

import java.util.Date;
import java.util.List;

@Mapper
public interface InventoryMapper {
    // 新增库存记录
    int insertInventory(Inventory inventory);
    
    // 修改库存记录
    void updateInventory(Inventory inventory);
    
    // 删除库存记录
    void deleteInventory(String inventory_id);
    
    // 根据ID查询库存记录
    Inventory selectInventoryById(String inventory_id);
    
    // 查询所有库存记录
    List<Inventory> selectAllInventories();
    
    // 根据药品ID查询库存记录
    List<Inventory> selectInventoriesByDrugId(String drug_id);
    
    // 根据仓库ID查询库存记录
    List<Inventory> selectInventoriesByWarehouseId(String warehouse_id);
    
    // 根据批号查询库存记录
    List<Inventory> selectInventoriesByBatchNo(String batch_no);
    
    // 根据药品ID和仓库ID查询库存
    Inventory selectInventoryByDrugAndWarehouse(String drug_id, String warehouse_id);
    
    // 更新库存数量
    void updateInventoryQuantity(String inventory_id, int quantity);

    // 查询即将过期的库存
    List<Inventory> selectInventoriesNearExpiry(Date daysBeforeExpiry);

    // 查询库存不足的药品
    List<Inventory> selectLowStockInventories(int threshold);
}