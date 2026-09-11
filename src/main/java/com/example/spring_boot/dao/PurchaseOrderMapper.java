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
            "VALUES (#{poId}, #{supplierId}, #{employeeId}, #{poDate}, #{auditStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "poId")
    int insertPurchaseOrder(PurchaseOrder purchaseOrder);
    
    @Update("UPDATE purchaseorder SET " +
            "supplier_id = #{supplierId}, employee_id = #{employeeId}, " +
            "po_date = #{poDate}, audit_status = #{auditStatus} " +
            "WHERE po_id = #{poId}")
    int updatePurchaseOrder(PurchaseOrder purchaseOrder);
    
    @Delete("DELETE FROM purchaseorder WHERE po_id = #{poId}")
    int deletePurchaseOrder(String po_id);
    
    @Select("SELECT * FROM purchaseorder WHERE po_id = #{poId}")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    PurchaseOrder selectPurchaseOrderById(String po_id);
    
    @Select("SELECT * FROM purchaseorder ORDER BY po_id")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectAllPurchaseOrders();
    
    @Select("SELECT * FROM purchaseorder WHERE supplier_id = #{supplierId} ORDER BY po_date DESC")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersBySupplierId(String supplier_id);
    
    @Select("SELECT * FROM purchaseorder WHERE audit_status = #{auditStatus} ORDER BY po_date DESC")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByAuditStatus(Integer audit_status);
    
    @Select("SELECT * FROM purchaseorder WHERE employee_id = #{employeeId} ORDER BY po_date DESC")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByEmployeeId(String employee_id);
    
    @Select({"<script>",
            "SELECT * FROM purchaseorder ",
            "WHERE po_date BETWEEN #{start_date} AND #{end_date} ",
            "ORDER BY po_date DESC",
            "</script>"})
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByDateRangeString(@Param("start_date") String start_date, @Param("end_date") String end_date);
    
    @Update("UPDATE purchaseorder SET audit_status = #{auditStatus} WHERE po_id = #{poId}")
    int updatePurchaseOrderAuditStatus(@Param("poId") String po_id, @Param("auditStatus") String audit_status);

    @Select("SELECT * FROM purchaseorder WHERE employee_id = #{creator} ORDER BY po_date DESC")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByCreateBy(String creator);

    @Select({"<script>",
            "SELECT * FROM purchaseorder ",
            "WHERE po_date BETWEEN #{startDate} AND #{endDate} ",
            "ORDER BY po_date DESC",
            "</script>"})
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByDateRangeDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT * FROM purchaseorder WHERE audit_status = #{status} ORDER BY po_date DESC")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersByStatus(String status);

    @Select("SELECT COALESCE(SUM(quantity * price), 0) " +
            "FROM purchaseorderitem " +
            "WHERE po_id = #{poId}")
    BigDecimal calculateOrderTotal(String poId);
    @Select("SELECT * FROM purchaseorder ORDER BY po_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "poId", column = "po_id"),
        @Result(property = "supplierId", column = "supplier_id"),
        @Result(property = "employeeId", column = "employee_id"),
        @Result(property = "poDate", column = "po_date"),
        @Result(property = "auditStatus", column = "audit_status")
    })
    List<PurchaseOrder> selectPurchaseOrdersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM purchaseorder")
    long countPurchaseOrders();

}