package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.SalesOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface SalesOrderMapper {
    
    @Insert("INSERT INTO salesorder (" +
            "so_id, customer_id, so_date, employee_id) " +
            "VALUES (#{so_id}, #{customer_id}, #{so_date}, #{employee_id})")
    @Options(useGeneratedKeys = true, keyProperty = "so_id")
    int insertSalesOrder(SalesOrder salesOrder);
    
    @Update("UPDATE salesorder SET " +
            "customer_id = #{customer_id}, so_date = #{so_date}, " +
            "employee_id = #{employee_id} " +
            "WHERE so_id = #{so_id}")
    int updateSalesOrder(SalesOrder salesOrder);
    
    @Delete("DELETE FROM salesorder WHERE so_id = #{so_id}")
    int deleteSalesOrder(String so_id);
    
    @Select("SELECT * FROM salesorder WHERE so_id = #{so_id}")
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    SalesOrder selectSalesOrderById(String so_id);
    
    @Select("SELECT * FROM salesorder ORDER BY so_date DESC")
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectAllSalesOrders();
    
    @Select("SELECT * FROM salesorder WHERE customer_id = #{customer_id} ORDER BY so_date DESC")
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByCustomerId(String customer_id);
    
    @Select("SELECT * FROM salesorder WHERE employee_id = #{employee_id} ORDER BY so_date DESC")
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByEmployeeId(String employee_id);
    
    @Select("SELECT * FROM salesorder WHERE employee_id = #{createBy} ORDER BY so_date DESC")
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByCreateBy(String createBy);

    @Select({"<script>",
            "SELECT * FROM salesorder ",
            "WHERE so_date BETWEEN #{start_date} AND #{end_date} ",
            "ORDER BY so_date DESC",
            "</script>"})
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByDateRangeString(@Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select({"<script>",
            "SELECT * FROM salesorder ",
            "WHERE so_date BETWEEN #{startDate} AND #{endDate} ",
            "ORDER BY so_date DESC",
            "</script>"})
    @Results({
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "customer_id", column = "customer_id"),
        @Result(property = "so_date", column = "so_date"),
        @Result(property = "employee_id", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByDateRangeDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT COALESCE(SUM(quantity * price), 0) " +
            "FROM salesorderitem " +
            "WHERE so_id = #{so_id}")
    BigDecimal calculateOrderTotal(String so_id);
}