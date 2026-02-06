package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
            "VALUES (#{customer_id}, #{name}, #{type}, #{contact_phone})")
    @Options(useGeneratedKeys = true, keyProperty = "customer_id")
    int insertCustomer(Customer customer);
    
    @Update("UPDATE customer SET " +
            "name = #{name}, type = #{type}, contact_phone = #{contact_phone} " +
            "WHERE customer_id = #{customer_id}")
    int updateCustomer(Customer customer);
    
    @Delete("DELETE FROM customer WHERE customer_id = #{customer_id}")
    int deleteCustomer(String customer_id);
    
    @Select("SELECT * FROM customer WHERE customer_id = #{customer_id}")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    Customer selectCustomerById(String customer_id);
    
    @Select("SELECT * FROM customer ORDER BY customer_id")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    List<Customer> selectAllCustomers();
    
    @Select("SELECT * FROM customer WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY customer_id")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    List<Customer> selectCustomersByName(String name);
    
    @Select("SELECT * FROM customer WHERE contact_phone = #{contact_phone}")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    List<Customer> selectCustomersByContactPhone(String contact_phone);
    
    @Select("SELECT * FROM customer WHERE type = #{type} ORDER BY customer_id")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    List<Customer> selectCustomersByType(String type);

    @Select("SELECT * FROM customer WHERE contact_phone = #{phone}")
    @Results({
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "type", column = "type"),
        @Result(property = "contact_phone", column = "contact_phone")
    })
    Customer selectCustomerByPhone(String phone);
}