package com.example.spring_boot.service;

import com.example.spring_boot.mapper.WarehouseMapper;
import com.example.spring_boot.pojo.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    // 新增仓库
    public int addWarehouse(Warehouse warehouse) {
        return warehouseMapper.insertWarehouse(warehouse);
    }

    // 修改仓库信息
    public void updateWarehouse(Warehouse warehouse) {
        warehouseMapper.updateWarehouse(warehouse);
    }

    // 删除仓库
    public void deleteWarehouse(String id) {
        warehouseMapper.deleteWarehouse(id);
    }

    // 根据ID查询仓库
    public Warehouse getWarehouseById(String id) {
        return warehouseMapper.selectWarehouseById(id);
    }

    // 查询所有仓库
    public List<Warehouse> getAllWarehouses() {
        return warehouseMapper.selectAllWarehouses();
    }

    // 根据名称查询仓库
    public List<Warehouse> getWarehousesByName(String name) {
        return warehouseMapper.selectWarehousesByName(name);
    }

    // 根据位置查询仓库
    public List<Warehouse> getWarehousesByLocation(String location) {
        return warehouseMapper.selectWarehousesByLocation(location);
    }
}