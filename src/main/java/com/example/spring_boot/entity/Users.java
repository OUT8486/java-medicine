package com.example.spring_boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private String user_id;
    private String user_name;
    private String password;
    private String role;
}