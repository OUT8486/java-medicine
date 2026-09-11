package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    private String drugId;          // 药品ID
    private String genericName;     // 通用名
    private String approvalNo;      // 批准文号
    private String dosageForm;      // 剂型
    private String specification;    // 规格
    private String unit;             // 单位
    private BigDecimal purchasePrice; // 采购价
    private BigDecimal retailPrice;   // 零售价
    private String manufacturerId;    // 生产厂家ID
}