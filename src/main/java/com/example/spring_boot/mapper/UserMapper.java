package com.example.spring_boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.pojo.Users;

@Mapper
public interface UserMapper {
    String LoginUser(String user_name,String password);

    int RegisterUser(Users user);

    String OutUser();

    int insertUser(Users user);
}
