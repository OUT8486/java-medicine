package com.example.spring_boot.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    private String poId;            // 采购订单ID
    private String supplierId;      // 供应商ID
    private String employeeId;      // 经办人ID
    @NotBlank(message = "采购日期不能为空")
    private String poDate;          // 采购日期
    private Integer auditStatus;    // 审核状态
    private List<PurchaseOrderItem> items;  // 订单明细（可选，创建时一并写入）
}
