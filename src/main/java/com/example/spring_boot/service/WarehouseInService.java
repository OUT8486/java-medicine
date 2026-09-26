package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.WarehouseInMapper;
import com.example.spring_boot.entity.WarehouseIn;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public int addWarehouseIn(WarehouseIn warehouseIn) {
        int result = warehouseInMapper.insertWarehouseIn(warehouseIn);
        if (result > 0) {
            redisUtils.delete(WAREHOUSE_IN_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改入库单
    @Transactional
    public void updateWarehouseIn(WarehouseIn warehouseIn) {
        warehouseInMapper.updateWarehouseIn(warehouseIn);
        redisUtils.evict(WAREHOUSE_IN_CACHE_KEY + warehouseIn.getWiId(), WAREHOUSE_IN_LIST_CACHE_KEY);
    }

    // 删除入库单
    @Transactional
    public void deleteWarehouseIn(String wiId) {
        warehouseInMapper.deleteWarehouseIn(wiId);
        redisUtils.evict(WAREHOUSE_IN_CACHE_KEY + wiId, WAREHOUSE_IN_LIST_CACHE_KEY);
    }

    // 根据ID查询入库单
    public WarehouseIn getWarehouseInById(String wiId) {
        return redisUtils.getOrLoad(WAREHOUSE_IN_CACHE_KEY + wiId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> warehouseInMapper.selectWarehouseInById(wiId));
    }

    // 查询所有入库单
    public List<WarehouseIn> getAllWarehouseIns() {
        return redisUtils.getListOrLoad(WAREHOUSE_IN_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                warehouseInMapper::selectAllWarehouseIns);
    }

    public PageResult<WarehouseIn> getWarehouseInsPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<WarehouseIn> list = warehouseInMapper.selectWarehouseInsPage(offset, s);
        long total = warehouseInMapper.countWarehouseIns();
        return new PageResult<>(list, total, p, s);
    }

}
