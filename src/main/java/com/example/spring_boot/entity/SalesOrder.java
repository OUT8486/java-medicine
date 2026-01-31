package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    private String so_id;            // 销售订单ID
    private String customer_id;      // 客户ID
    private String so_date;          // 销售日期
    private String employee_id;      // 经办人编号
}