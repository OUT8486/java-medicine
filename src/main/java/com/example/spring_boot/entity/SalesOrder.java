package com.example.spring_boot.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    private String soId;            // 销售订单ID
    @NotBlank(message = "客户不能为空")
    private String customerId;      // 客户ID
    @NotBlank(message = "销售日期不能为空")
    private String soDate;          // 销售日期
    private String employeeId;      // 经办人编号
    private List<SalesOrderItem> items;  // 订单明细（可选，创建时一并写入）
}
