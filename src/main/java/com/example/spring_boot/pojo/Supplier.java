package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private String supplier_id;      // 供应商编号
    private String name;             // 供应商名称
    private String contact_phone;    // 联系电话
    private Integer status;           // 合作状态（0停用/1启用）
}