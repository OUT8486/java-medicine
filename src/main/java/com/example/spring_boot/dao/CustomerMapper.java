package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {
    
    @Insert("INSERT INTO customer (" +
            "customer_id, name, type, contact_phone) " +
            "VALUES (#{customerId}, #{name}, #{type}, #{contactPhone})")
    @Options(useGeneratedKeys = true, keyProperty = "customerId")
    int insertCustomer(Customer customer);
    
    @Update("UPDATE customer SET " +
            "name = #{name}, type = #{type}, contact_phone = #{contactPhone} " +
            "WHERE customer_id = #{customerId}")
    int updateCustomer(Customer customer);
    
    @Delete("DELETE FROM customer WHERE customer_id = #{customerId}")
    int deleteCustomer(String customer_id);
    
    @Select("SELECT * FROM customer WHERE customer_id = #{customerId}")
    Customer selectCustomerById(String customer_id);
    
    @Select("SELECT * FROM customer ORDER BY customer_id")
    List<Customer> selectAllCustomers();
    

    @Select("SELECT * FROM customer ORDER BY customer_id LIMIT #{offset}, #{size}")
    List<Customer> selectCustomersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM customer")
    long countCustomers();
}