package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.spring_boot.entity.Users;

@Mapper
public interface UserMapper {
    
    @Select("SELECT user_name FROM users WHERE user_name = #{user_name} AND password = #{password}")
    String LoginUser(@Param("user_name") String user_name, @Param("password") String password);

    @Insert("INSERT INTO users (user_id, user_name, password, role) " +
            "VALUES (#{user_id}, #{user_name}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    int RegisterUser(Users user);

    @Select("SELECT 'logout successful'")
    String OutUser();

    @Insert("INSERT INTO users (user_id, user_name, password, role) " +
            "VALUES (#{user_id}, #{user_name}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    int insertUser(Users user);
}