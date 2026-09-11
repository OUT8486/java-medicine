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
            "VALUES (#{soId}, #{customerId}, #{soDate}, #{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "soId")
    int insertSalesOrder(SalesOrder salesOrder);
    
    @Update("UPDATE salesorder SET " +
            "customer_id = #{customerId}, so_date = #{soDate}, " +
            "employee_id = #{employeeId} " +
            "WHERE so_id = #{soId}")
    int updateSalesOrder(SalesOrder salesOrder);
    
    @Delete("DELETE FROM salesorder WHERE so_id = #{soId}")
    int deleteSalesOrder(String so_id);
    
    @Select("SELECT * FROM salesorder WHERE so_id = #{soId}")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    SalesOrder selectSalesOrderById(String so_id);
    
    @Select("SELECT * FROM salesorder ORDER BY so_date DESC")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectAllSalesOrders();
    
    @Select("SELECT * FROM salesorder WHERE customer_id = #{customerId} ORDER BY so_date DESC")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByCustomerId(String customer_id);
    
    @Select("SELECT * FROM salesorder WHERE employee_id = #{employeeId} ORDER BY so_date DESC")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByEmployeeId(String employee_id);
    
    @Select("SELECT * FROM salesorder WHERE employee_id = #{createBy} ORDER BY so_date DESC")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByCreateBy(String createBy);

    @Select({"<script>",
            "SELECT * FROM salesorder ",
            "WHERE so_date BETWEEN #{start_date} AND #{end_date} ",
            "ORDER BY so_date DESC",
            "</script>"})
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByDateRangeString(@Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select({"<script>",
            "SELECT * FROM salesorder ",
            "WHERE so_date BETWEEN #{startDate} AND #{endDate} ",
            "ORDER BY so_date DESC",
            "</script>"})
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersByDateRangeDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT COALESCE(SUM(quantity * price), 0) " +
            "FROM salesorderitem " +
            "WHERE so_id = #{soId}")
    BigDecimal calculateOrderTotal(String so_id);
    @Select("SELECT * FROM salesorder ORDER BY so_date DESC LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "soId", column = "so_id"),
        @Result(property = "customerId", column = "customer_id"),
        @Result(property = "soDate", column = "so_date"),
        @Result(property = "employeeId", column = "employee_id")
    })
    List<SalesOrder> selectSalesOrdersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM salesorder")
    long countSalesOrders();

}