package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String employeeId;      // 员工编号
    private String name;             // 员工姓名
    private String post;             // 岗位（采购/销售/库管/审核等）
}