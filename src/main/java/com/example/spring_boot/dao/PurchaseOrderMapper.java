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

import com.example.spring_boot.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface PurchaseOrderMapper {
    
    @Insert("INSERT INTO purchaseorder (" +
            "po_id, supplier_id, employee_id, po_date, audit_status) " +
            "VALUES (#{po_id}, #{supplier_id}, #{employee_id}, #{po_date}, #{audit_status})")
    @Options(useGeneratedKeys = true, keyProperty = "po_id")
    int insertPurchaseOrder(PurchaseOrder purchaseOrder);
    
    @Update("UPDATE purchaseorder SET " +
            "supplier_id = #{supplier_id}, employee_id = #{employee_id}, " +
            "po_date = #{po_date}, audit_status = #{audit_status} " +
            "WHERE po_id = #{po_id}")
    int updatePurchaseOrder(PurchaseOrder purchaseOrder);
    
    @Delete("DELETE FROM purchaseorder WHERE po_id = #{po_id}")
    int deletePurchaseOrder(String po_id);
    
    @Select("SELECT * FROM purchaseorder WHERE po_id = #{po_id}")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    PurchaseOrder selectPurchaseOrderById(String po_id);
    
    @Select("SELECT * FROM purchaseorder ORDER BY po_id")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectAllPurchaseOrders();
    
    @Select("SELECT * FROM purchaseorder WHERE supplier_id = #{supplier_id} ORDER BY po_date DESC")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersBySupplierId(String supplier_id);
    
    @Select("SELECT * FROM purchaseorder WHERE audit_status = #{audit_status} ORDER BY po_date DESC")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByAuditStatus(Integer audit_status);
    
    @Select("SELECT * FROM purchaseorder WHERE employee_id = #{employee_id} ORDER BY po_date DESC")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByEmployeeId(String employee_id);
    
    @Select({"<script>",
            "SELECT * FROM purchaseorder ",
            "WHERE po_date BETWEEN #{start_date} AND #{end_date} ",
            "ORDER BY po_date DESC",
            "</script>"})
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByDateRangeString(@Param("start_date") String start_date, @Param("end_date") String end_date);
    
    @Update("UPDATE purchaseorder SET audit_status = #{audit_status} WHERE po_id = #{po_id}")
    int updatePurchaseOrderAuditStatus(@Param("po_id") String po_id, @Param("audit_status") String audit_status);

    @Select("SELECT * FROM purchaseorder WHERE employee_id = #{creator} ORDER BY po_date DESC")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByCreateBy(String creator);

    @Select({"<script>",
            "SELECT * FROM purchaseorder ",
            "WHERE po_date BETWEEN #{startDate} AND #{endDate} ",
            "ORDER BY po_date DESC",
            "</script>"})
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByDateRangeDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT * FROM purchaseorder WHERE audit_status = #{status} ORDER BY po_date DESC")
    @Results({
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "supplier_id", column = "supplier_id"),
        @Result(property = "employee_id", column = "employee_id"),
        @Result(property = "po_date", column = "po_date"),
        @Result(property = "audit_status", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByStatus(String status);

    @Select("SELECT COALESCE(SUM(quantity * price), 0) " +
            "FROM purchaseorderitem " +
            "WHERE po_id = #{poId}")
    BigDecimal calculateOrderTotal(String poId);
}