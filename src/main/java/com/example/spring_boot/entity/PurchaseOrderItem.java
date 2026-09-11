package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem {
    private String poiId;           // 采购订单项编号
    private String poId;            // 采购订单编号
    private String drugId;          // 药品编号
    private Integer quantity;        // 采购数量
    private BigDecimal price;        // 采购单价
}