package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    Customer selectCustomerById(String customer_id);
    
    @Select("SELECT * FROM customer ORDER BY customer_id")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    List<Customer> selectAllCustomers();
    
    @Select("SELECT * FROM customer WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY customer_id")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    List<Customer> selectCustomersByName(String name);
    
    @Select("SELECT * FROM customer WHERE contact_phone = #{contactPhone}")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    List<Customer> selectCustomersByContactPhone(String contact_phone);
    
    @Select("SELECT * FROM customer WHERE type = #{type} ORDER BY customer_id")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    List<Customer> selectCustomersByType(String type);

    @Select("SELECT * FROM customer WHERE contact_phone = #{phone}")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    Customer selectCustomerByPhone(String phone);

    @Select("SELECT * FROM customer ORDER BY customer_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contactPhone", column = "contact_phone")
    })
    List<Customer> selectCustomersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM customer")
    long countCustomers();
}