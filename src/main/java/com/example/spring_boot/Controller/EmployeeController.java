package com.example.spring_boot.controller;
import jakarta.validation.Valid;
import com.example.spring_boot.config.RequireRole;
import com.example.spring_boot.config.Role;

import com.example.spring_boot.entity.Employee;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * 获取所有员工列表
     * GET /api/employees
     */
    @GetMapping
    public Result<List<Employee>> list() {
        List<Employee> employees = employeeService.getAllEmployees();
        return Result.success(employees);
    }

    /**
     * 根据 ID 获取员工详情
     * GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return Result.success(employee);
        } else {
            return Result.error(404, "员工不存在");
        }
    }

    /**
     * 新增员工
     * POST /api/employees
     */
    @RequireRole(Role.ADMIN)
    @PostMapping
    public Result<String> add(@Valid @RequestBody Employee employee) {
        if (employee == null) {
            return Result.error(400, "请求体为空");
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            return Result.error(400, "员工姓名不能为空");
        }
        if (employee.getPost() == null || employee.getPost().trim().isEmpty()) {
            return Result.error(400, "职位不能为空");
        }

        try {
            int result = employeeService.addEmployee(employee);
            if (result > 0) {
                return Result.success("员工添加成功");
            } else {
                return Result.error("员工添加失败");
            }
        } catch (Exception e) {
            log.error("员工添加失败", e);
            return Result.error(500, "员工添加失败，请稍后重试");
        }
    }

    /**
     * 修改员工信息
     * PUT /api/employees/{id}
     */
    @RequireRole(Role.ADMIN)
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @Valid @RequestBody Employee employee) {
        if (employee == null) {
            return Result.error(400, "请求体为空");
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            return Result.error(400, "员工姓名不能为空");
        }
        if (employee.getPost() == null || employee.getPost().trim().isEmpty()) {
            return Result.error(400, "职位不能为空");
        }

        try {
            employee.setEmployee_id(id);
            employeeService.updateEmployee(employee);
            return Result.success("员工更新成功");
        } catch (Exception e) {
            log.error("员工更新失败", e);
            return Result.error(500, "员工更新失败，请稍后重试");
        }
    }

    /**
     * 删除员工
     * DELETE /api/employees/{id}
     */
    @RequireRole(Role.ADMIN)
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return Result.success("员工删除成功");
        } catch (Exception e) {
            log.error("员工删除失败", e);
            return Result.error(500, "员工删除失败，请稍后重试");
        }
    }
}