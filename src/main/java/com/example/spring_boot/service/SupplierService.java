package com.example.spring_boot.service;

import com.example.spring_boot.mapper.SupplierMapper;
import com.example.spring_boot.pojo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    // 新增供应商
    public int addSupplier(Supplier supplier) {
        return supplierMapper.insertSupplier(supplier);
    }

    // 修改供应商信息
    public void updateSupplier(Supplier supplier) {
        supplierMapper.updateSupplier(supplier);
    }

    // 删除供应商
    public void deleteSupplier(String supplierId) {
        supplierMapper.deleteSupplier(supplierId);
    }

    // 根据ID查询供应商
    public Supplier getSupplierById(String supplierId) {
        return supplierMapper.selectSupplierById(supplierId);
    }

    // 查询所有供应商
    public List<Supplier> getAllSuppliers() {
        return supplierMapper.selectAllSuppliers();
    }

    // 根据名称查询供应商
    public List<Supplier> getSuppliersByName(String name) {
        return supplierMapper.selectSuppliersByName(name);
    }
}