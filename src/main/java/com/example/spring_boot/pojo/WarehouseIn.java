package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseIn {
    private String wi_id;            // 入库单ID
    private String po_id;            // 采购订单ID
    private String warehouse_id;     // 仓库ID
    private String in_date;          // 入库日期
    private String batch_no;         // 药品批号
    private String validity_date;    // 有效期
}