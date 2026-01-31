package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem {
    private String poi_id;           // 采购明细ID
    private String po_id;            // 采购订单ID
    private String drug_id;          // 药品ID
    private Integer quantity;        // 采购数量
    private BigDecimal price;        // 单价
    private BigDecimal subtotal;     // 小计
    
}