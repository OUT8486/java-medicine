package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Warehouse;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    // 新增仓库
    int insertWarehouse(Warehouse warehouse);
    
    // 修改仓库信息
    void updateWarehouse(Warehouse warehouse);
    
    // 删除仓库
    void deleteWarehouse(String warehouse_id);
    
    // 根据ID查询仓库
    Warehouse selectWarehouseById(String warehouse_id);
    
    // 查询所有仓库
    List<Warehouse> selectAllWarehouses();
    
    // 根据名称查询仓库
    List<Warehouse> selectWarehousesByName(String name);
    
    // 根据地址查询仓库
    List<Warehouse> selectWarehousesByAddress(String address);
    
    // 根据位置查询仓库（兼容方法）
    default List<Warehouse> selectWarehousesByLocation(String location) {
        return selectWarehousesByAddress(location);
    }
}