package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Inventory;

import java.util.List;

@Mapper
public interface InventoryMapper {
    
    @Insert("INSERT INTO inventory (" +
            "inventory_id, drug_id, warehouse_id, batch_no, quantity, validity_date) " +
            "VALUES (#{inventoryId}, #{drugId}, #{warehouseId}, #{batchNo}, #{quantity}, #{validityDate})")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryId")
    int insertInventory(Inventory inventory);
    
    @Update("UPDATE inventory SET " +
            "drug_id = #{drugId}, warehouse_id = #{warehouseId}, batch_no = #{batchNo}, " +
            "quantity = #{quantity}, validity_date = #{validityDate} " +
            "WHERE inventory_id = #{inventoryId}")
    int updateInventory(Inventory inventory);
    
    @Delete("DELETE FROM inventory WHERE inventory_id = #{inventoryId}")
    int deleteInventory(String inventory_id);
    
    @Select("SELECT * FROM inventory WHERE inventory_id = #{inventoryId}")
    Inventory selectInventoryById(String inventory_id);
    
    @Select("SELECT * FROM inventory ORDER BY inventory_id")
    List<Inventory> selectAllInventories();
    

    @Select("SELECT * FROM inventory ORDER BY inventory_id LIMIT #{offset}, #{size}")
    List<Inventory> selectInventoriesPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM inventory")
    long countInventories();

}