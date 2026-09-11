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
            "VALUES (#{manufacturerId}, #{name}, #{creditCode})")
    @Options(useGeneratedKeys = true, keyProperty = "manufacturerId")
    int insertManufacturer(Manufacturer manufacturer);
    
    @Update("UPDATE manufacturer SET " +
            "name = #{name}, credit_code = #{creditCode} " +
            "WHERE manufacturer_id = #{manufacturerId}")
    int updateManufacturer(Manufacturer manufacturer);
    
    @Delete("DELETE FROM manufacturer WHERE manufacturer_id = #{manufacturerId}")
    int deleteManufacturer(String manufacturer_id);
    
    @Select("SELECT * FROM manufacturer WHERE manufacturer_id = #{manufacturerId}")
    @Results({
        @Result(property = "manufacturerId", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "creditCode", column = "credit_code")
    })
    Manufacturer selectManufacturerById(String manufacturer_id);
    
    @Select("SELECT * FROM manufacturer ORDER BY manufacturer_id")
    @Results({
        @Result(property = "manufacturerId", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "creditCode", column = "credit_code")
    })
    List<Manufacturer> selectAllManufacturers();
    
    @Select("SELECT * FROM manufacturer WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "manufacturerId", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "creditCode", column = "credit_code")
    })
    List<Manufacturer> selectManufacturersByName(String name);
    
    @Select("SELECT * FROM manufacturer WHERE credit_code = #{creditCode}")
    @Results({
        @Result(property = "manufacturerId", column = "manufacturer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "creditCode", column = "credit_code")
    })
    Manufacturer selectManufacturerByCreditCode(String credit_code);
}