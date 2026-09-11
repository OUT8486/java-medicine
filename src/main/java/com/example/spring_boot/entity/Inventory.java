package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private String inventoryId;     // 库存ID
    private String drugId;          // 药品ID
    private String warehouseId;     // 仓库ID
    private String batchNo;         // 批号
    private Integer quantity;        // 库存数量
    private String validityDate;    // 有效期
}