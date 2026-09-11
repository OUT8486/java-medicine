package com.example.spring_boot.service;

import com.example.spring_boot.dao.CustomerMapper;
import com.example.spring_boot.entity.Customer;
import com.example.spring_boot.entity.PageResult;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String CUSTOMER_CACHE_KEY = "customer:";
    private static final String CUSTOMER_LIST_CACHE_KEY = "customer:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增客户
    public int addCustomer(Customer customer) {
        int result = customerMapper.insertCustomer(customer);
        if (result > 0) {
            redisUtils.delete(CUSTOMER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改客户信息
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
        redisUtils.delete(CUSTOMER_CACHE_KEY + customer.getCustomer_id());
        redisUtils.delete(CUSTOMER_LIST_CACHE_KEY);
    }

    // 删除客户
    public void deleteCustomer(String customerId) {
        customerMapper.deleteCustomer(customerId);
        redisUtils.delete(CUSTOMER_CACHE_KEY + customerId);
        redisUtils.delete(CUSTOMER_LIST_CACHE_KEY);
    }

    // 根据ID查询客户
    public Customer getCustomerById(String customerId) {
        String cacheKey = CUSTOMER_CACHE_KEY + customerId;
        Customer customer = (Customer) redisUtils.get(cacheKey);
        
        if (customer != null) {
            return customer;
        }
        
        customer = customerMapper.selectCustomerById(customerId);
        if (customer != null) {
            redisUtils.set(cacheKey, customer, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return customer;
    }

    // 查询所有客户
    public List<Customer> getAllCustomers() {
        List<Customer> customers = (List<Customer>) redisUtils.get(CUSTOMER_LIST_CACHE_KEY);
        
        if (customers != null) {
            return customers;
        }
        
        customers = customerMapper.selectAllCustomers();
        if (customers != null && !customers.isEmpty()) {
            redisUtils.set(CUSTOMER_LIST_CACHE_KEY, customers, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return customers;
    }

    // 根据姓名查询客户
    public List<Customer> getCustomersByName(String name) {
        return customerMapper.selectCustomersByName(name);
    }

    // 分页查询客户
    public PageResult<Customer> getCustomersPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Customer> list = customerMapper.selectCustomersPage(offset, s);
        long total = customerMapper.countCustomers();
        return new PageResult<>(list, total, p, s);
    }

}