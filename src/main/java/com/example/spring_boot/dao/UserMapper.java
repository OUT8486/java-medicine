package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.spring_boot.entity.Users;

@Mapper
public interface UserMapper {

    /**
     * 按用户名查询用户（用于登录时比对 BCrypt 密码哈希）。
     */
    @Select("SELECT user_id, user_name, password, role FROM users WHERE user_name = #{userName}")
    Users selectUserByUsername(@Param("userName") String user_name);

    /**
     * 新增用户：密码由 Service 层先做 BCrypt 加密再入库。
     */
    @Insert("INSERT INTO users (user_id, user_name, password, role) " +
            "VALUES (#{userId}, #{userName}, #{password}, #{role})")
    int RegisterUser(Users user);
}
