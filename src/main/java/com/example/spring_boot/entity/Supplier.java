package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private String supplierId;      // 供应商编号
    private String name;             // 供应商名称
    private String contactPhone;    // 联系电话
    private Integer status;           // 合作状态（0停用/1启用）
}