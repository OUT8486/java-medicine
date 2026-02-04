package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    private String manufacturer_id;  // 厂家编号
    private String name;             // 生产厂家名称
    private String credit_code;      // 统一社会信用代码
}