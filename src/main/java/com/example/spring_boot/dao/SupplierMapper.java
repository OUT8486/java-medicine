package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Supplier;

import java.util.List;

@Mapper
public interface SupplierMapper {
    // 新增供应商
    int insertSupplier(Supplier supplier);
    
    // 修改供应商信息
    void updateSupplier(Supplier supplier);
    
    // 删除供应商
    void deleteSupplier(String supplier_id);
    
    // 根据ID查询供应商
    Supplier selectSupplierById(String supplier_id);
    
    // 查询所有供应商
    List<Supplier> selectAllSuppliers();
    
    // 根据名称查询供应商
    List<Supplier> selectSuppliersByName(String name);
    
    // 根据联系电话查询供应商
    List<Supplier> selectSuppliersByContactPhone(String contact_phone);
    
    // 根据状态查询供应商
    List<Supplier> selectSuppliersByStatus(Integer status);
}