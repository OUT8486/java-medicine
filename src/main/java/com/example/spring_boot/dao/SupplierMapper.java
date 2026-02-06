package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Supplier;

import java.util.List;

@Mapper
public interface SupplierMapper {
    
    @Insert("INSERT INTO supplier (" +
            "supplier_id, name, contact_phone, status) " +
            "VALUES (#{supplier_id}, #{name}, #{contact_phone}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "supplier_id")
    int insertSupplier(Supplier supplier);
    
    @Update("UPDATE supplier SET " +
            "name = #{name}, contact_phone = #{contact_phone}, status = #{status} " +
            "WHERE supplier_id = #{supplier_id}")
    int updateSupplier(Supplier supplier);
    
    @Delete("DELETE FROM supplier WHERE supplier_id = #{supplier_id}")
    int deleteSupplier(String supplier_id);
    
    @Select("SELECT * FROM supplier WHERE supplier_id = #{supplier_id}")
    @Results({
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contact_phone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    Supplier selectSupplierById(String supplier_id);
    
    @Select("SELECT * FROM supplier ORDER BY supplier_id")
    @Results({
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contact_phone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectAllSuppliers();
    
    @Select("SELECT * FROM supplier WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contact_phone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByName(String name);
    
    @Select("SELECT * FROM supplier WHERE contact_phone = #{contact_phone}")
    @Results({
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contact_phone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByContactPhone(String contact_phone);
    
    @Select("SELECT * FROM supplier WHERE status = #{status}")
    @Results({
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contact_phone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByStatus(Integer status);
}