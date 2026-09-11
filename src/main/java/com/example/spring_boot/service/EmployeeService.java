package com.example.spring_boot.service;
import com.example.spring_boot.entity.PageResult;

import com.example.spring_boot.dao.EmployeeMapper;
import com.example.spring_boot.entity.Employee;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String EMPLOYEE_CACHE_KEY = "employee:";
    private static final String EMPLOYEE_LIST_CACHE_KEY = "employee:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增员工
    public int addEmployee(Employee employee) {
        int result = employeeMapper.insertEmployee(employee);
        if (result > 0) {
            redisUtils.delete(EMPLOYEE_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改员工信息
    public void updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
        redisUtils.delete(EMPLOYEE_CACHE_KEY + employee.getEmployee_id());
        redisUtils.delete(EMPLOYEE_LIST_CACHE_KEY);
    }

    // 删除员工
    public void deleteEmployee(String id) {
        employeeMapper.deleteEmployee(id);
        redisUtils.delete(EMPLOYEE_CACHE_KEY + id);
        redisUtils.delete(EMPLOYEE_LIST_CACHE_KEY);
    }

    // 根据ID查询员工
    public Employee getEmployeeById(String id) {
        String cacheKey = EMPLOYEE_CACHE_KEY + id;
        Employee employee = (Employee) redisUtils.get(cacheKey);
        
        if (employee != null) {
            return employee;
        }
        
        employee = employeeMapper.selectEmployeeById(id);
        if (employee != null) {
            redisUtils.set(cacheKey, employee, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return employee;
    }

    // 查询所有员工
    public List<Employee> getAllEmployees() {
        List<Employee> employees = (List<Employee>) redisUtils.get(EMPLOYEE_LIST_CACHE_KEY);
        
        if (employees != null) {
            return employees;
        }
        
        employees = employeeMapper.selectAllEmployees();
        if (employees != null && !employees.isEmpty()) {
            redisUtils.set(EMPLOYEE_LIST_CACHE_KEY, employees, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return employees;
    }

    // 根据姓名查询员工
    public List<Employee> getEmployeesByName(String name) {
        return employeeMapper.selectEmployeesByName(name);
    }

    // 根据岗位查询员工
    public List<Employee> getEmployeesByPost(String post) {
        return employeeMapper.selectEmployeesByPost(post);
    }

    public PageResult<Employee> getEmployeesPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Employee> list = employeeMapper.selectEmployeesPage(offset, s);
        long total = employeeMapper.countEmployees();
        return new PageResult<>(list, total, p, s);
    }

}