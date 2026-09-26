package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
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
    Supplier selectSupplierById(String supplier_id);
    
    @Select("SELECT * FROM supplier ORDER BY supplier_id")
    List<Supplier> selectAllSuppliers();
    

    @Select("SELECT * FROM supplier ORDER BY supplier_id LIMIT #{offset}, #{size}")
    List<Supplier> selectSuppliersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM supplier")
    long countSuppliers();
}