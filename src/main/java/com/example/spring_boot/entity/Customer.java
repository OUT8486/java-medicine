package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String customer_id;      // 客户编号
    private String name;             // 客户姓名/企业名称
    private String type;             // 客户类型（个人/企业）
    private String contact_phone;    // 联系电话
}