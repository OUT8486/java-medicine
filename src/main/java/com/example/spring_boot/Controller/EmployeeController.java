package com.example.spring_boot.Controller;

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

    // 3. 接口：分页查询员工数据
    @GetMapping("/employee/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEmployeesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String post) {

        // 获取员工列表
        List<Employee> employees;
        if (post != null && !post.isEmpty()) {
            employees = employeeMapper.selectEmployeesByPost(post);
        } else {
            employees = employeeMapper.selectAllEmployees();
        }
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("employees", employees);
        result.put("total", employees.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
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

    // 5. 接口：根据姓名查询员工
    @GetMapping("/employee/name/{name}")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeesByName(@PathVariable String name) {
        List<Employee> employees = employeeMapper.selectEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    // 6. 接口：根据部门查询员工
    @GetMapping("/employee/department/{department}")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        // 数据库只存储'post'列，使用'post'作为岗位过滤
        List<Employee> employees = employeeMapper.selectEmployeesByPost(department);
        return ResponseEntity.ok(employees);
    }

    // 7. 接口：根据职位查询员工
    @GetMapping("/employee/position/{position}")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeesByPosition(@PathVariable String position) {
        // 与岗位等价，delegates to select by post
        List<Employee> employees = employeeMapper.selectEmployeesByPost(position);
        return ResponseEntity.ok(employees);
    }

    // 8. 接口：员工登录验证
    @PostMapping("/employee/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> employeeLogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        Employee employee = employeeMapper.selectByUsernameAndPassword(username, password);
        
        Map<String, Object> result = new HashMap<>();
        if (employee != null) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("employee", employee);
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
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
        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "position 不能为空");
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
        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); 
            r.put("message", "position 不能为空");
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