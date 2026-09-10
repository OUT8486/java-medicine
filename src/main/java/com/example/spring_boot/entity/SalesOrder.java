package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    private String so_id;            // 销售订单ID
    private String customer_id;      // 客户ID
    private String so_date;          // 销售日期
    private String employee_id;      // 经办人编号
    private List<SalesOrderItem> items;  // 订单明细（可选，创建时一并写入）
}
