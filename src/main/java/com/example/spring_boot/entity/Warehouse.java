package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private String warehouse_id;     // 仓库编号
    private String name;             // 仓库名称
    private String location;         // 仓库地址/位置
}