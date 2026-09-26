package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.WarehouseMapper;
import com.example.spring_boot.entity.Warehouse;
import com.example.spring_boot.utils.IdGenerator;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String WAREHOUSE_CACHE_KEY = "warehouse:";
    private static final String WAREHOUSE_LIST_CACHE_KEY = "warehouse:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增仓库
    public int addWarehouse(Warehouse warehouse) {
        if (warehouse.getWarehouseId() == null || warehouse.getWarehouseId().isBlank()) {
            warehouse.setWarehouseId(IdGenerator.next("WH"));
        }
        int result = warehouseMapper.insertWarehouse(warehouse);
        if (result > 0) {
            redisUtils.delete(WAREHOUSE_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改仓库信息
    public void updateWarehouse(Warehouse warehouse) {
        warehouseMapper.updateWarehouse(warehouse);
        redisUtils.evict(WAREHOUSE_CACHE_KEY + warehouse.getWarehouseId(), WAREHOUSE_LIST_CACHE_KEY);
    }

    // 删除仓库
    public void deleteWarehouse(String id) {
        warehouseMapper.deleteWarehouse(id);
        redisUtils.evict(WAREHOUSE_CACHE_KEY + id, WAREHOUSE_LIST_CACHE_KEY);
    }

    // 根据ID查询仓库
    public Warehouse getWarehouseById(String id) {
        return redisUtils.getOrLoad(WAREHOUSE_CACHE_KEY + id, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> warehouseMapper.selectWarehouseById(id));
    }

    // 查询所有仓库
    public List<Warehouse> getAllWarehouses() {
        return redisUtils.getListOrLoad(WAREHOUSE_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                warehouseMapper::selectAllWarehouses);
    }


    public PageResult<Warehouse> getWarehousesPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Warehouse> list = warehouseMapper.selectWarehousesPage(offset, s);
        long total = warehouseMapper.countWarehouses();
        return new PageResult<>(list, total, p, s);
    }

}