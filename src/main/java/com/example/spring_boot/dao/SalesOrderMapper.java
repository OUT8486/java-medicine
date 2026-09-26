package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.SalesOrder;

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
    SalesOrder selectSalesOrderById(String so_id);
    
    @Select("SELECT * FROM salesorder ORDER BY so_date DESC")
    List<SalesOrder> selectAllSalesOrders();
    

    @Select("SELECT * FROM salesorder ORDER BY so_date DESC LIMIT #{offset}, #{size}")
    List<SalesOrder> selectSalesOrdersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM salesorder")
    long countSalesOrders();

}