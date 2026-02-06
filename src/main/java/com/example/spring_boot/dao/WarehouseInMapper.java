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

import com.example.spring_boot.entity.WarehouseIn;

import java.util.List;

@Mapper
public interface WarehouseInMapper {
    
    @Insert("INSERT INTO warehouse_in (" +
            "record_id, drug_id, warehouse_id, quantity, batch_number, " +
            "production_date, expiry_date, purchase_price, in_date, operator_id) " +
            "VALUES (#{record_id}, #{drug_id}, #{warehouse_id}, #{quantity}, #{batch_number}, " +
            "#{production_date}, #{expiry_date}, #{purchase_price}, #{in_date}, #{operator_id})")
    @Options(useGeneratedKeys = true, keyProperty = "record_id")
    int insertWarehouseIn(WarehouseIn warehouseIn);
    
    @Update("UPDATE warehouse_in SET " +
            "drug_id = #{drug_id}, warehouse_id = #{warehouse_id}, quantity = #{quantity}, " +
            "batch_number = #{batch_number}, production_date = #{production_date}, " +
            "expiry_date = #{expiry_date}, purchase_price = #{purchase_price}, " +
            "in_date = #{in_date}, operator_id = #{operator_id} " +
            "WHERE record_id = #{record_id}")
    int updateWarehouseIn(WarehouseIn warehouseIn);
    
    @Delete("DELETE FROM warehouse_in WHERE record_id = #{record_id}")
    int deleteWarehouseIn(String record_id);
    
    @Select("SELECT * FROM warehouse_in WHERE record_id = #{record_id}")
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    WarehouseIn selectWarehouseInById(String record_id);
    
    @Select("SELECT * FROM warehouse_in ORDER BY record_id")
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    List<WarehouseIn> selectAllWarehouseIns();
    
    @Select("SELECT * FROM warehouse_in WHERE drug_id = #{po_id}")
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    List<WarehouseIn> selectWarehouseInsByPoId(String po_id);
    
    @Select("SELECT * FROM warehouse_in WHERE warehouse_id = #{warehouse_id}")
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    List<WarehouseIn> selectWarehouseInsByWarehouseId(String warehouse_id);
    
    @Select({"<script>",
            "SELECT * FROM warehouse_in ",
            "WHERE in_date BETWEEN #{start_date} AND #{end_date} ",
            "ORDER BY record_id",
            "</script>"})
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    List<WarehouseIn> selectWarehouseInsByDateRange(@Param("start_date") String start_date, @Param("end_date") String end_date);
    
    @Select("SELECT * FROM warehouse_in WHERE batch_number = #{batch_no}")
    @Results({
        @Result(property = "record_id", column = "record_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "warehouse_id", column = "warehouse_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "batch_number", column = "batch_number"),
        @Result(property = "production_date", column = "production_date"),
        @Result(property = "expiry_date", column = "expiry_date"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "in_date", column = "in_date"),
        @Result(property = "operator_id", column = "operator_id")
    })
    List<WarehouseIn> selectWarehouseInsByBatchNo(String batch_no);
}