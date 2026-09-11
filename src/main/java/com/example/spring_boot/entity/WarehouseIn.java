package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseIn {
    private String wiId;            // 入库单ID
    private String poId;            // 采购订单ID
    private String warehouseId;     // 仓库ID
    private String inDate;          // 入库日期
    private String batchNo;         // 药品批号
    private String validityDate;    // 有效期
}