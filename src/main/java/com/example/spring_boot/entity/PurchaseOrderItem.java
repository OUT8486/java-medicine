package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem {
    private String poi_id;           // 采购订单项编号
    private String po_id;            // 采购订单编号
    private String drug_id;          // 药品编号
    private Integer quantity;        // 采购数量
    private BigDecimal price;        // 采购单价
}