package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    private String drug_id;          // 药品ID
    private String generic_name;     // 通用名
    private String approval_no;      // 批准文号
    private String dosage_form;      // 剂型
    private String specification;    // 规格
    private String unit;             // 单位
    private BigDecimal purchase_price; // 采购价
    private BigDecimal retail_price;   // 零售价
    private String manufacturer_id;    // 生产厂家ID
}