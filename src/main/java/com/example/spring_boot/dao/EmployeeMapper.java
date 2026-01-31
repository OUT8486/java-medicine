package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    // 新增员工
    int insertEmployee(Employee employee);
    
    // 修改员工信息
    void updateEmployee(Employee employee);
    
    // 删除员工
    void deleteEmployee(String employee_id);
    
    // 根据ID查询员工
    Employee selectEmployeeById(String employee_id);
    
    // 查询所有员工
    List<Employee> selectAllEmployees();
    
    // 根据名称查询员工
    List<Employee> selectEmployeesByName(String name);
    
    // 根据岗位查询员工
    List<Employee> selectEmployeesByPost(String post);

    // 员工登录验证
    Employee selectByUsernameAndPassword(String username, String password);
}