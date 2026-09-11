package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Inventory;

import java.util.Date;
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
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    Inventory selectInventoryById(String inventory_id);
    
    @Select("SELECT * FROM inventory ORDER BY inventory_id")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectAllInventories();
    
    @Select("SELECT * FROM inventory WHERE drug_id = #{drugId}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectInventoriesByDrugId(String drug_id);
    
    @Select("SELECT * FROM inventory WHERE warehouse_id = #{warehouseId}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectInventoriesByWarehouseId(String warehouse_id);
    
    @Select("SELECT * FROM inventory WHERE batch_no = #{batchNo}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectInventoriesByBatchNo(String batch_no);
    
    @Select("SELECT * FROM inventory WHERE drug_id = #{drugId} AND warehouse_id = #{warehouseId}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    Inventory selectInventoryByDrugAndWarehouse(@Param("drugId") String drug_id, @Param("warehouseId") String warehouse_id);
    
    @Update("UPDATE inventory SET quantity = #{quantity} WHERE inventory_id = #{inventoryId}")
    int updateInventoryQuantity(@Param("inventoryId") String inventory_id, @Param("quantity") int quantity);

    @Select("SELECT * FROM inventory WHERE validity_date <= #{daysBeforeExpiry}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectInventoriesNearExpiry(Date daysBeforeExpiry);

    @Select("SELECT * FROM inventory WHERE quantity <= #{threshold}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectLowStockInventories(int threshold);
    @Select("SELECT * FROM inventory ORDER BY inventory_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "inventoryId", column = "inventory_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<Inventory> selectInventoriesPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM inventory")
    long countInventories();

}