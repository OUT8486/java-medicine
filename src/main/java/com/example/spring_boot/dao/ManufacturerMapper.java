package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Manufacturer;

import java.util.List;

@Mapper
public interface ManufacturerMapper {
    
    @Insert("INSERT INTO manufacturer (" +
            "manufacturer_id, name, credit_code) " +
            "VALUES (#{manufacturer_id}, #{name}, #{credit_code})")
    @Options(useGeneratedKeys = true, keyProperty = "manufacturer_id")
    int insertManufacturer(Manufacturer manufacturer);
    
    @Update("UPDATE manufacturer SET " +
            "name = #{name}, credit_code = #{credit_code} " +
            "WHERE manufacturer_id = #{manufacturer_id}")
    int updateManufacturer(Manufacturer manufacturer);
    
    @Delete("DELETE FROM manufacturer WHERE manufacturer_id = #{manufacturer_id}")
    int deleteManufacturer(String manufacturer_id);
    
    @Select("SELECT * FROM manufacturer WHERE manufacturer_id = #{manufacturer_id}")
    @Results({
        @Result(property = "manufacturer_id", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "credit_code", column = "credit_code")
    })
    Manufacturer selectManufacturerById(String manufacturer_id);
    
    @Select("SELECT * FROM manufacturer ORDER BY manufacturer_id")
    @Results({
        @Result(property = "manufacturer_id", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "credit_code", column = "credit_code")
    })
    List<Manufacturer> selectAllManufacturers();
    
    @Select("SELECT * FROM manufacturer WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "manufacturer_id", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "credit_code", column = "credit_code")
    })
    List<Manufacturer> selectManufacturersByName(String name);
    
    @Select("SELECT * FROM manufacturer WHERE credit_code = #{credit_code}")
    @Results({
        @Result(property = "manufacturer_id", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "credit_code", column = "credit_code")
    })
    Manufacturer selectManufacturerByCreditCode(String credit_code);
}