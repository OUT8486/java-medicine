package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    private String po_id;            // 采购订单ID
    private String supplier_id;      // 供应商ID
    private String employee_id;      // 经办人ID
    private String po_date;          // 采购日期
    private Integer audit_status;    // 审核状态
    private List<PurchaseOrderItem> items;  // 订单明细（可选，创建时一并写入）
}
