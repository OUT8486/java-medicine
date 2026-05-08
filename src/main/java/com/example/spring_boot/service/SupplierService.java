package com.example.spring_boot.service;

import com.example.spring_boot.dao.SupplierMapper;
import com.example.spring_boot.entity.Supplier;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String SUPPLIER_CACHE_KEY = "supplier:";
    private static final String SUPPLIER_LIST_CACHE_KEY = "supplier:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增供应商
    public int addSupplier(Supplier supplier) {
        int result = supplierMapper.insertSupplier(supplier);
        if (result > 0) {
            redisUtils.delete(SUPPLIER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改供应商信息
    public void updateSupplier(Supplier supplier) {
        supplierMapper.updateSupplier(supplier);
        redisUtils.delete(SUPPLIER_CACHE_KEY + supplier.getSupplier_id());
        redisUtils.delete(SUPPLIER_LIST_CACHE_KEY);
    }

    // 删除供应商
    public void deleteSupplier(String supplierId) {
        supplierMapper.deleteSupplier(supplierId);
        redisUtils.delete(SUPPLIER_CACHE_KEY + supplierId);
        redisUtils.delete(SUPPLIER_LIST_CACHE_KEY);
    }

    // 根据ID查询供应商
    public Supplier getSupplierById(String supplierId) {
        String cacheKey = SUPPLIER_CACHE_KEY + supplierId;
        Supplier supplier = (Supplier) redisUtils.get(cacheKey);
        
        if (supplier != null) {
            return supplier;
        }
        
        supplier = supplierMapper.selectSupplierById(supplierId);
        if (supplier != null) {
            redisUtils.set(cacheKey, supplier, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return supplier;
    }

    // 查询所有供应商
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = (List<Supplier>) redisUtils.get(SUPPLIER_LIST_CACHE_KEY);
        
        if (suppliers != null) {
            return suppliers;
        }
        
        suppliers = supplierMapper.selectAllSuppliers();
        if (suppliers != null && !suppliers.isEmpty()) {
            redisUtils.set(SUPPLIER_LIST_CACHE_KEY, suppliers, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return suppliers;
    }

    // 根据名称查询供应商
    public List<Supplier> getSuppliersByName(String name) {
        return supplierMapper.selectSuppliersByName(name);
    }
}