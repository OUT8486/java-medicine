package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    private String po_id;            // 采购订单ID
    private String supplier_id;      // 供应商ID
    private String employee_id;      // 经办人ID
    private String po_date;          // 采购日期
    private Integer audit_status;    // 审核状态
}