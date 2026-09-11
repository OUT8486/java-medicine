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
            "VALUES (#{inventory_id}, #{drug_id}, #{warehouse_id}, #{batch_no}, #{quantity}, #{validity_date})")
    @Options(useGeneratedKeys = true, keyProperty = "inventory_id")
    int insertInventory(Inventory inventory);
    
    @Update("UPDATE inventory SET " +
            "drug_id = #{drug_id}, warehouse_id = #{warehouse_id}, batch_no = #{batch_no}, " +
            "quantity = #{quantity}, validity_date = #{validity_date} " +
            "WHERE inventory_id = #{inventory_id}")
    int updateInventory(Inventory inventory);
    
    @Delete("DELETE FROM inventory WHERE inventory_id = #{inventory_id}")
    int deleteInventory(String inventory_id);
    
    @Select("SELECT * FROM inventory WHERE inventory_id = #{inventory_id}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    Inventory selectInventoryById(String inventory_id);
    
    @Select("SELECT * FROM inventory ORDER BY inventory_id")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectAllInventories();
    
    @Select("SELECT * FROM inventory WHERE drug_id = #{drug_id}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectInventoriesByDrugId(String drug_id);
    
    @Select("SELECT * FROM inventory WHERE warehouse_id = #{warehouse_id}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectInventoriesByWarehouseId(String warehouse_id);
    
    @Select("SELECT * FROM inventory WHERE batch_no = #{batch_no}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectInventoriesByBatchNo(String batch_no);
    
    @Select("SELECT * FROM inventory WHERE drug_id = #{drug_id} AND warehouse_id = #{warehouse_id}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    Inventory selectInventoryByDrugAndWarehouse(@Param("drug_id") String drug_id, @Param("warehouse_id") String warehouse_id);
    
    @Update("UPDATE inventory SET quantity = #{quantity} WHERE inventory_id = #{inventory_id}")
    int updateInventoryQuantity(@Param("inventory_id") String inventory_id, @Param("quantity") int quantity);

    @Select("SELECT * FROM inventory WHERE validity_date <= #{daysBeforeExpiry}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectInventoriesNearExpiry(Date daysBeforeExpiry);

    @Select("SELECT * FROM inventory WHERE quantity <= #{threshold}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectLowStockInventories(int threshold);
    @Select("SELECT * FROM inventory ORDER BY inventory_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "inventory_id", column = "inventory_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "batch_no", column = "batch_no"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "validity_date", column = "validity_date")
    })
    List<Inventory> selectInventoriesPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM inventory")
    long countInventories();

}