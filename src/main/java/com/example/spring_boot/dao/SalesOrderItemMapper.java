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

import com.example.spring_boot.entity.SalesOrderItem;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SalesOrderItemMapper {
    
    @Insert("INSERT INTO salesorderitem (" +
            "soi_id, so_id, drug_id, quantity, price, batch_no) " +
            "VALUES (#{soiId}, #{soId}, #{drugId}, #{quantity}, #{price}, #{batchNo})")
    @Options(useGeneratedKeys = true, keyProperty = "soiId")
    int insertSalesOrderItem(SalesOrderItem salesOrderItem);
    
    @Update("UPDATE salesorderitem SET " +
            "so_id = #{soId}, drug_id = #{drugId}, quantity = #{quantity}, " +
            "price = #{price}, batch_no = #{batchNo} " +
            "WHERE soi_id = #{soiId}")
    int updateSalesOrderItem(SalesOrderItem salesOrderItem);
    
    @Delete("DELETE FROM salesorderitem WHERE soi_id = #{soiId}")
    int deleteSalesOrderItem(String soi_id);
    
    @Select("SELECT * FROM salesorderitem WHERE soi_id = #{soiId}")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    SalesOrderItem selectSalesOrderItemById(String soi_id);
    
    @Select("SELECT * FROM salesorderitem ORDER BY soi_id")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    List<SalesOrderItem> selectAllSalesOrderItems();
    
    @Select("SELECT * FROM salesorderitem WHERE so_id = #{soId}")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsBySoId(String so_id);
    
    @Select("SELECT * FROM salesorderitem WHERE drug_id = #{drugId}")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsByDrugId(String drug_id);
    
    @Select("SELECT * FROM salesorderitem WHERE batch_no = #{batchNo}")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsByBatchNo(String batch_no);
    
    @Insert({"<script>",
            "INSERT INTO salesorderitem (soi_id, so_id, drug_id, quantity, price, batch_no) VALUES ",
            "<foreach collection='items' item='item' separator=','>",
            "(#{item.soiId}, #{item.soId}, #{item.drugId}, #{item.quantity}, #{item.price}, #{item.batchNo})",
            "</foreach>",
            "</script>"})
    int batchInsertSalesOrderItems(@Param("items") List<SalesOrderItem> items);
    
    @Delete("DELETE FROM salesorderitem WHERE so_id = #{soId}")
    int deleteSalesOrderItemsBySoId(String so_id);
    @Select("SELECT * FROM salesorderitem ORDER BY soi_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "soiId", column = "soi_id"),
        @Result(property = "soId", column = "so_id"),
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batchNo", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM salesorderitem")
    long countSalesOrderItems();

}