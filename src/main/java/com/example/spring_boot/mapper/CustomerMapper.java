package com.example.spring_boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.pojo.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {
    // 新增客户
    int insertCustomer(Customer customer);
    
    // 修改客户信息
    void updateCustomer(Customer customer);
    
    // 删除客户
    void deleteCustomer(String customer_id);
    
    // 根据ID查询客户
    Customer selectCustomerById(String customer_id);
    
    // 查询所有客户
    List<Customer> selectAllCustomers();
    
    // 根据姓名查询客户
    List<Customer> selectCustomersByName(String name);
    
    // 根据联系电话查询客户
    List<Customer> selectCustomersByContactPhone(String contact_phone);
    
    // 根据客户类型查询客户
    List<Customer> selectCustomersByType(String type);

    // 根据联系电话查询客户
    Customer selectCustomerByPhone(String phone);
}