package com.example.spring_boot.dao;

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
            "VALUES (#{warehouse_id}, #{name}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "warehouse_id")
    int insertWarehouse(Warehouse warehouse);
    
    @Update("UPDATE warehouse SET " +
            "name = #{name}, location = #{location} " +
            "WHERE warehouse_id = #{warehouse_id}")
    int updateWarehouse(Warehouse warehouse);
    
    @Delete("DELETE FROM warehouse WHERE warehouse_id = #{warehouse_id}")
    int deleteWarehouse(String warehouse_id);
    
    @Select("SELECT * FROM warehouse WHERE warehouse_id = #{warehouse_id}")
    @Results({
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    Warehouse selectWarehouseById(String warehouse_id);
    
    @Select("SELECT * FROM warehouse ORDER BY warehouse_id")
    @Results({
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectAllWarehouses();
    
    @Select("SELECT * FROM warehouse WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectWarehousesByName(String name);
    
    @Select("SELECT * FROM warehouse WHERE location LIKE CONCAT('%', #{location}, '%')")
    @Results({
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "location", column = "location")
    })
    List<Warehouse> selectWarehousesByLocation(String location);
}