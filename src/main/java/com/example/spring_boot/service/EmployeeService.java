package com.example.spring_boot.service;

import com.example.spring_boot.dao.EmployeeMapper;
import com.example.spring_boot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    // 新增员工
    public int addEmployee(Employee employee) {
        return employeeMapper.insertEmployee(employee);
    }

    // 修改员工信息
    public void updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    // 删除员工
    public void deleteEmployee(String id) {
        employeeMapper.deleteEmployee(id);
    }

    // 根据ID查询员工
    public Employee getEmployeeById(String id) {
        return employeeMapper.selectEmployeeById(id);
    }

    // 查询所有员工
    public List<Employee> getAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }

    // 根据姓名查询员工
    public List<Employee> getEmployeesByName(String name) {
        return employeeMapper.selectEmployeesByName(name);
    }

    // 根据岗位查询员工
    public List<Employee> getEmployeesByPost(String post) {
        return employeeMapper.selectEmployeesByPost(post);
    }

    // 根据用户名和密码查询员工
    public Employee getEmployeeByUsernameAndPassword(String username, String password) {
        return employeeMapper.selectByUsernameAndPassword(username, password);
    }
}