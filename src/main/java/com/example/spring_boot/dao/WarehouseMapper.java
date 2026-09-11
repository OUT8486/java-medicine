package com.example.spring_boot.dao;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Warehouse;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    
    @Insert("INSERT INTO warehouse (" +
            "warehouse_id, name, location) " +
            "VALUES (#{warehouseId}, #{name}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "warehouseId")
    int insertWarehouse(Warehouse warehouse);
    
    @Update("UPDATE warehouse SET " +
            "name = #{name}, location = #{location} " +
            "WHERE warehouse_id = #{warehouseId}")
    int updateWarehouse(Warehouse warehouse);
    
    @Delete("DELETE FROM warehouse WHERE warehouse_id = #{warehouseId}")
    int deleteWarehouse(String warehouse_id);
    
    @Select("SELECT * FROM warehouse WHERE warehouse_id = #{warehouseId}")
    @Results({
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    Warehouse selectWarehouseById(String warehouse_id);
    
    @Select("SELECT * FROM warehouse ORDER BY warehouse_id")
    @Results({
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectAllWarehouses();
    
    @Select("SELECT * FROM warehouse WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectWarehousesByName(String name);
    
    @Select("SELECT * FROM warehouse WHERE location LIKE CONCAT('%', #{location}, '%')")
    @Results({
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectWarehousesByLocation(String location);
    @Select("SELECT * FROM warehouse ORDER BY warehouse_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectWarehousesPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM warehouse")
    long countWarehouses();

}