package com.example.spring_boot.service;

import com.example.spring_boot.dao.SupplierMapper;
import com.example.spring_boot.entity.PageResult;
import com.example.spring_boot.entity.Supplier;
import com.example.spring_boot.utils.IdGenerator;
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
        if (supplier.getSupplierId() == null || supplier.getSupplierId().isBlank()) {
            supplier.setSupplierId(IdGenerator.next("SU"));
        }
        int result = supplierMapper.insertSupplier(supplier);
        if (result > 0) {
            redisUtils.delete(SUPPLIER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改供应商信息
    public void updateSupplier(Supplier supplier) {
        supplierMapper.updateSupplier(supplier);
        redisUtils.evict(SUPPLIER_CACHE_KEY + supplier.getSupplierId(), SUPPLIER_LIST_CACHE_KEY);
    }

    // 删除供应商
    public void deleteSupplier(String supplierId) {
        supplierMapper.deleteSupplier(supplierId);
        redisUtils.evict(SUPPLIER_CACHE_KEY + supplierId, SUPPLIER_LIST_CACHE_KEY);
    }

    // 根据ID查询供应商
    public Supplier getSupplierById(String supplierId) {
        return redisUtils.getOrLoad(SUPPLIER_CACHE_KEY + supplierId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> supplierMapper.selectSupplierById(supplierId));
    }

    // 查询所有供应商
    public List<Supplier> getAllSuppliers() {
        return redisUtils.getListOrLoad(SUPPLIER_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                supplierMapper::selectAllSuppliers);
    }

    // 分页查询供应商
    public PageResult<Supplier> getSuppliersPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Supplier> list = supplierMapper.selectSuppliersPage(offset, s);
        long total = supplierMapper.countSuppliers();
        return new PageResult<>(list, total, p, s);
    }
}
