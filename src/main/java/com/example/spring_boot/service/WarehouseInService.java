package com.example.spring_boot.service;

import com.example.spring_boot.dao.WarehouseInMapper;
import com.example.spring_boot.entity.WarehouseIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseInService {
    @Autowired
    private WarehouseInMapper warehouseInMapper;

    // 新增入库单
    public int addWarehouseIn(WarehouseIn warehouseIn) {
        return warehouseInMapper.insertWarehouseIn(warehouseIn);
    }

    // 修改入库单
    public void updateWarehouseIn(WarehouseIn warehouseIn) {
        warehouseInMapper.updateWarehouseIn(warehouseIn);
    }

    // 删除入库单
    public void deleteWarehouseIn(String wiId) {
        warehouseInMapper.deleteWarehouseIn(wiId);
    }

    // 根据ID查询入库单
    public WarehouseIn getWarehouseInById(String wiId) {
        return warehouseInMapper.selectWarehouseInById(wiId);
    }

    // 查询所有入库单
    public List<WarehouseIn> getAllWarehouseIns() {
        return warehouseInMapper.selectAllWarehouseIns();
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