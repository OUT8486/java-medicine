package com.example.spring_boot.service;

import com.example.spring_boot.dao.CustomerMapper;
import com.example.spring_boot.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    // 新增客户
    public int addCustomer(Customer customer) {
        return customerMapper.insertCustomer(customer);
    }

    // 修改客户信息
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
    }

    // 删除客户
    public void deleteCustomer(String customerId) {
        customerMapper.deleteCustomer(customerId);
    }

    // 根据ID查询客户
    public Customer getCustomerById(String customerId) {
        return customerMapper.selectCustomerById(customerId);
    }

    // 查询所有客户
    public List<Customer> getAllCustomers() {
        return customerMapper.selectAllCustomers();
    }

    // 根据姓名查询客户
    public List<Customer> getCustomersByName(String name) {
        return customerMapper.selectCustomersByName(name);
    }

    // 根据电话查询客户
    public Customer getCustomerByPhone(String phone) {
        return customerMapper.selectCustomerByPhone(phone);
    }
}