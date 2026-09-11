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

import com.example.spring_boot.entity.Supplier;

import java.util.List;

@Mapper
public interface SupplierMapper {
    
    @Insert("INSERT INTO supplier (" +
            "supplier_id, name, contact_phone, status) " +
            "VALUES (#{supplierId}, #{name}, #{contactPhone}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "supplierId")
    int insertSupplier(Supplier supplier);
    
    @Update("UPDATE supplier SET " +
            "name = #{name}, contact_phone = #{contactPhone}, status = #{status} " +
            "WHERE supplier_id = #{supplierId}")
    int updateSupplier(Supplier supplier);
    
    @Delete("DELETE FROM supplier WHERE supplier_id = #{supplierId}")
    int deleteSupplier(String supplier_id);
    
    @Select("SELECT * FROM supplier WHERE supplier_id = #{supplierId}")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    Supplier selectSupplierById(String supplier_id);
    
    @Select("SELECT * FROM supplier ORDER BY supplier_id")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectAllSuppliers();
    
    @Select("SELECT * FROM supplier WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByName(String name);
    
    @Select("SELECT * FROM supplier WHERE contact_phone = #{contactPhone}")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByContactPhone(String contact_phone);
    
    @Select("SELECT * FROM supplier WHERE status = #{status}")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersByStatus(Integer status);

    @Select("SELECT * FROM supplier ORDER BY supplier_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "status", column = "status")
    })
    List<Supplier> selectSuppliersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM supplier")
    long countSuppliers();
}