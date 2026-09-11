package com.example.spring_boot.dao;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    
    @Insert("INSERT INTO employee (" +
            "employee_id, name, post) " +
            "VALUES (#{employeeId}, #{name}, #{post})")
    @Options(useGeneratedKeys = true, keyProperty = "employeeId")
    int insertEmployee(Employee employee);
    
    @Update("UPDATE employee SET " +
            "name = #{name}, post = #{post} " +
            "WHERE employee_id = #{employeeId}")
    int updateEmployee(Employee employee);
    
    @Delete("DELETE FROM employee WHERE employee_id = #{employeeId}")
    int deleteEmployee(String employee_id);
    
    @Select("SELECT * FROM employee WHERE employee_id = #{employeeId}")
    @Results({
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "post", column = "post")
    })
    Employee selectEmployeeById(String employee_id);
    
    @Select("SELECT * FROM employee ORDER BY employee_id")
    @Results({
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "post", column = "post")
    })
    List<Employee> selectAllEmployees();
    
    @Select("SELECT * FROM employee WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY employee_id")
    @Results({
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "post", column = "post")
    })
    List<Employee> selectEmployeesByName(String name);
    
    @Select("SELECT * FROM employee WHERE post = #{post} ORDER BY employee_id")
    @Results({
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "post", column = "post")
    })
    List<Employee> selectEmployeesByPost(String post);
    @Select("SELECT * FROM employee ORDER BY employee_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "post", column = "post")
    })
    List<Employee> selectEmployeesPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM employee")
    long countEmployees();

}
