package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItem {
    private String soi_id;           // 销售明细ID
    private String so_id;            // 销售订单ID
    private String drug_id;          // 药品ID
    private Integer quantity;        // 销售数量
    private BigDecimal price;        // 单价
    private BigDecimal subtotal;     // 小计
    
}