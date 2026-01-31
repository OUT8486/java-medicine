package com.example.spring_boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.pojo.WarehouseIn;

import java.util.List;

@Mapper
public interface WarehouseInMapper {
    // 新增入库单
    int insertWarehouseIn(WarehouseIn warehouseIn);
    
    // 修改入库单
    void updateWarehouseIn(WarehouseIn warehouseIn);
    
    // 删除入库单
    void deleteWarehouseIn(String wi_id);
    
    // 根据ID查询入库单
    WarehouseIn selectWarehouseInById(String wi_id);
    
    // 查询所有入库单
    List<WarehouseIn> selectAllWarehouseIns();
    
    // 根据采购订单ID查询入库单
    List<WarehouseIn> selectWarehouseInsByPoId(String po_id);
    
    // 根据仓库ID查询入库单
    List<WarehouseIn> selectWarehouseInsByWarehouseId(String warehouse_id);
    
    // 根据日期范围查询入库单
    List<WarehouseIn> selectWarehouseInsByDateRange(String start_date, String end_date);
    
    // 根据批号查询入库单
    List<WarehouseIn> selectWarehouseInsByBatchNo(String batch_no);
}