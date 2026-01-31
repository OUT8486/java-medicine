package com.example.spring_boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String employee_id;      // 员工编号
    private String name;             // 员工姓名
    private String position;         // 职位
    private String contact_phone;    // 联系电话
    private Integer status;          // 在职状态（0离职/1在职）
}