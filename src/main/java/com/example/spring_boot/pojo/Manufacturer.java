package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    private String manufacturer_id;  // 生产厂家编号
    private String name;             // 生产厂家名称
    private String address;          // 地址
    private String contact_person;   // 联系人
    private String contact_phone;    // 联系电话
    private Integer status;          // 合作状态（0停用/1启用）
}