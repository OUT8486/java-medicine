package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Customer;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理控制器
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 获取所有客户列表
     * GET /api/customers
     */
    @GetMapping
    public Result<List<Customer>> list() {
        List<Customer> customers = customerService.getAllCustomers();
        return Result.success(customers);
    }

    /**
     * 根据 ID 获取客户详情
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public Result<Customer> getById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return Result.success(customer);
        } else {
            return Result.error(404, "客户不存在");
        }
    }

    /**
     * 新增客户
     * POST /api/customers
     */
    @PostMapping
    public Result<String> add(@RequestBody Customer customer) {
        // 基本校验
        if (customer == null) {
            return Result.error(400, "请求体为空");
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return Result.error(400, "客户名称不能为空");
        }
        if (customer.getType() == null || customer.getType().trim().isEmpty()) {
            return Result.error(400, "客户类型不能为空");
        }
        String phone = customer.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            return Result.error(400, "联系电话必须为 11 位数字");
        }

        try {
            int result = customerService.addCustomer(customer);
            if (result > 0) {
                return Result.success("客户添加成功");
            } else {
                return Result.error("客户添加失败");
            }
        } catch (Exception e) {
            return Result.error(500, "客户添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改客户信息
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody Customer customer) {
        if (customer == null) {
            return Result.error(400, "请求体为空");
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return Result.error(400, "客户名称不能为空");
        }
        if (customer.getType() == null || customer.getType().trim().isEmpty()) {
            return Result.error(400, "客户类型不能为空");
        }
        String phone = customer.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            return Result.error(400, "联系电话必须为 11 位数字");
        }

        try {
            customer.setCustomer_id(id);
            customerService.updateCustomer(customer);
            return Result.success("客户更新成功");
        } catch (Exception e) {
            return Result.error(500, "客户更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除客户
     * DELETE /api/customers/{id}
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return Result.success("客户删除成功");
        } catch (Exception e) {
            return Result.error(500, "客户删除失败：" + e.getMessage());
        }
    }
}