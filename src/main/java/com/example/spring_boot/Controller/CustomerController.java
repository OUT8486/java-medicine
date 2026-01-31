package com.example.spring_boot.Controller;

import com.example.spring_boot.mapper.CustomerMapper;
import com.example.spring_boot.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    // 1. 页面跳转：访问 http://localhost:8080/customer 跳转到客户管理页面
    @GetMapping("/customer")
    public String customerPage() {
        return "customer";
    }

    // 2. 接口：获取所有客户数据
    @GetMapping("/customer/list")
    @ResponseBody
    public List<Customer> getAllCustomers() {
        return customerMapper.selectAllCustomers();
    }

    // 3. 接口：分页查询客户数据
    @GetMapping("/customer/page")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCustomersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String contactPhone,
            @RequestParam(required = false) String type) {
        
        // 获取客户列表
        List<Customer> customers = customerMapper.selectAllCustomers();
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("customers", customers);
        result.put("total", customers.size());
        result.put("page", page);
        result.put("size", size);
        
        return ResponseEntity.ok(result);
    }

    // 4. 接口：根据ID获取客户
    @GetMapping("/customer/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerMapper.selectCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. 接口：根据姓名查询客户
    @GetMapping("/customer/name/{name}")
    @ResponseBody
    public ResponseEntity<List<Customer>> getCustomersByName(@PathVariable String name) {
        List<Customer> customers = customerMapper.selectCustomersByName(name);
        return ResponseEntity.ok(customers);
    }

    // 6. 接口：根据电话查询客户
    @GetMapping("/customer/phone/{phone}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerByPhone(@PathVariable String phone) {
        Customer customer = customerMapper.selectCustomerByPhone(phone);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. 接口：新增客户
    @PostMapping("/customer")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addCustomer(@RequestBody Customer customer) {
        // 基本校验
        if (customer == null) {
            Map<String, String> r = new HashMap<>(); r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (customer.getType() == null || customer.getType().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); r.put("message", "type 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        String phone = customer.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            Map<String, String> r = new HashMap<>(); r.put("message", "contact_phone 必须为 11 位数字");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            int result = customerMapper.insertCustomer(customer);
            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "客户添加成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "客户添加失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "客户添加失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 8. 接口：更新客户信息
    @PutMapping("/customer/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        // 基本校验
        if (customer == null) {
            Map<String, String> r = new HashMap<>(); r.put("message", "请求体为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); r.put("message", "name 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        if (customer.getType() == null || customer.getType().trim().isEmpty()) {
            Map<String, String> r = new HashMap<>(); r.put("message", "type 不能为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }
        String phone = customer.getContact_phone();
        if (phone != null && !phone.matches("^\\d{11}$")) {
            Map<String, String> r = new HashMap<>(); r.put("message", "contact_phone 必须为 11 位数字");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        }

        try {
            customer.setCustomer_id(id);
            customerMapper.updateCustomer(customer);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "客户更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "客户更新失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 9. 接口：删除客户
    @DeleteMapping("/customer/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable String id) {
        try {
            customerMapper.deleteCustomer(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "客户删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "客户删除失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}