package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItem {
    private String soi_id;           // 销售订单项编号
    private String so_id;            // 销售订单编号
    private String drug_id;          // 药品编号
    private Integer quantity;        // 销售数量
    private BigDecimal price;        // 销售单价
    private String batch_no;         // 药品批次号
}