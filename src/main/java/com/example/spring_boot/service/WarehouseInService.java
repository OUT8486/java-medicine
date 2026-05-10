package com.example.spring_boot.service;

import com.example.spring_boot.dao.WarehouseInMapper;
import com.example.spring_boot.entity.WarehouseIn;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WarehouseInService {
    @Autowired
    private WarehouseInMapper warehouseInMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String WAREHOUSE_IN_CACHE_KEY = "warehouse_in:";
    private static final String WAREHOUSE_IN_LIST_CACHE_KEY = "warehouse_in:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增入库单
    public int addWarehouseIn(WarehouseIn warehouseIn) {
        int result = warehouseInMapper.insertWarehouseIn(warehouseIn);
        if (result > 0) {
            redisUtils.delete(WAREHOUSE_IN_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改入库单
    public void updateWarehouseIn(WarehouseIn warehouseIn) {
        warehouseInMapper.updateWarehouseIn(warehouseIn);
        redisUtils.delete(WAREHOUSE_IN_CACHE_KEY + warehouseIn.getWi_id());
        redisUtils.delete(WAREHOUSE_IN_LIST_CACHE_KEY);
    }

    // 删除入库单
    public void deleteWarehouseIn(String wiId) {
        warehouseInMapper.deleteWarehouseIn(wiId);
        redisUtils.delete(WAREHOUSE_IN_CACHE_KEY + wiId);
        redisUtils.delete(WAREHOUSE_IN_LIST_CACHE_KEY);
    }

    // 根据ID查询入库单
    public WarehouseIn getWarehouseInById(String wiId) {
        String cacheKey = WAREHOUSE_IN_CACHE_KEY + wiId;
        WarehouseIn warehouseIn = (WarehouseIn) redisUtils.get(cacheKey);
        
        if (warehouseIn != null) {
            return warehouseIn;
        }
        
        warehouseIn = warehouseInMapper.selectWarehouseInById(wiId);
        if (warehouseIn != null) {
            redisUtils.set(cacheKey, warehouseIn, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return warehouseIn;
    }

    // 查询所有入库单
    public List<WarehouseIn> getAllWarehouseIns() {
        List<WarehouseIn> warehouseIns = (List<WarehouseIn>) redisUtils.get(WAREHOUSE_IN_LIST_CACHE_KEY);
        
        if (warehouseIns != null) {
            return warehouseIns;
        }
        
        warehouseIns = warehouseInMapper.selectAllWarehouseIns();
        if (warehouseIns != null && !warehouseIns.isEmpty()) {
            redisUtils.set(WAREHOUSE_IN_LIST_CACHE_KEY, warehouseIns, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return warehouseIns;
    }

    // 根据采购订单ID查询入库单
    public List<WarehouseIn> getWarehouseInsByPoId(String poId) {
        return warehouseInMapper.selectWarehouseInsByPoId(poId);
    }

    // 根据仓库ID查询入库单
    public List<WarehouseIn> getWarehouseInsByWarehouseId(String warehouseId) {
        return warehouseInMapper.selectWarehouseInsByWarehouseId(warehouseId);
    }

    // 根据批号查询入库单
    public List<WarehouseIn> getWarehouseInsByBatchNo(String batchNo) {
        return warehouseInMapper.selectWarehouseInsByBatchNo(batchNo);
    }
}