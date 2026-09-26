package com.example.spring_boot.service;

import com.example.spring_boot.dao.CustomerMapper;
import com.example.spring_boot.entity.Customer;
import com.example.spring_boot.entity.PageResult;
import com.example.spring_boot.utils.IdGenerator;
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
        if (customer.getCustomerId() == null || customer.getCustomerId().isBlank()) {
            customer.setCustomerId(IdGenerator.next("CU"));
        }
        int result = customerMapper.insertCustomer(customer);
        if (result > 0) {
            redisUtils.delete(CUSTOMER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改客户信息
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
        redisUtils.evict(CUSTOMER_CACHE_KEY + customer.getCustomerId(), CUSTOMER_LIST_CACHE_KEY);
    }

    // 删除客户
    public void deleteCustomer(String customerId) {
        customerMapper.deleteCustomer(customerId);
        redisUtils.evict(CUSTOMER_CACHE_KEY + customerId, CUSTOMER_LIST_CACHE_KEY);
    }

    // 根据ID查询客户
    public Customer getCustomerById(String customerId) {
        return redisUtils.getOrLoad(CUSTOMER_CACHE_KEY + customerId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> customerMapper.selectCustomerById(customerId));
    }

    // 查询所有客户
    public List<Customer> getAllCustomers() {
        return redisUtils.getListOrLoad(CUSTOMER_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                customerMapper::selectAllCustomers);
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
