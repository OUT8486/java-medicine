package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.PurchaseOrder;

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
    PurchaseOrder selectPurchaseOrderById(String po_id);
    
    @Select("SELECT * FROM purchaseorder ORDER BY po_id")
    List<PurchaseOrder> selectAllPurchaseOrders();
    

    @Select("SELECT * FROM purchaseorder ORDER BY po_id LIMIT #{offset}, #{size}")
    List<PurchaseOrder> selectPurchaseOrdersPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM purchaseorder")
    long countPurchaseOrders();

}