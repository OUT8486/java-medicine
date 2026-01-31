package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private String inventory_id;     // 库存ID
    private String drug_id;          // 药品ID
    private String warehouse_id;     // 仓库ID
    private String batch_no;         // 批号
    private Integer quantity;        // 库存数量
    private String validity_date;    // 有效期
}