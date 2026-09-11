package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItem {
    private String soiId;           // 销售订单项编号
    private String soId;            // 销售订单编号
    private String drugId;          // 药品编号
    private Integer quantity;        // 销售数量
    private BigDecimal price;        // 销售单价
    private String batchNo;         // 药品批次号
}