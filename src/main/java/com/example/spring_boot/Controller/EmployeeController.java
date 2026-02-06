package com.example.spring_boot.controller;

import com.example.spring_boot.dao.EmployeeMapper;
import com.example.spring_boot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    // 1. 页面跳转：访问http://localhost:8080/employee 跳转到员工管理页面
    @GetMapping("/employee")
    public String employeePage() {
        return "employee";
    }

    // 2. 接口：获取所有员工数据
    @GetMapping("/employee/list")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }

    // 4. 接口：根据ID获取员工
    @GetMapping("/employee/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeMapper.selectEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 9. 接口：新增员工
    @PostMapping("/employee")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addEmployee(@RequestBody Employee employee) {
        // 基本校验
        if (employee == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (employee.getPost() == null || employee.getPost().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "post 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            int result = employeeMapper.insertEmployee(employee);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "员工添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "员工添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "员工添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 10. 接口：更新员工信息
    @PutMapping("/employee/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        // 基本校验
        if (employee == null) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (employee.getPost() == null || employee.getPost().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "post 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            employee.setEmployee_id(id);
            employeeMapper.updateEmployee(employee);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "员工更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "员工更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 11. 接口：删除员工
    @DeleteMapping("/employee/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable String id) {
        try {
            employeeMapper.deleteEmployee(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "员工删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "员工删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}