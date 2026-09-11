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
            "VALUES (#{soi_id}, #{so_id}, #{drug_id}, #{quantity}, #{price}, #{batch_no})")
    @Options(useGeneratedKeys = true, keyProperty = "soi_id")
    int insertSalesOrderItem(SalesOrderItem salesOrderItem);
    
    @Update("UPDATE salesorderitem SET " +
            "so_id = #{so_id}, drug_id = #{drug_id}, quantity = #{quantity}, " +
            "price = #{price}, batch_no = #{batch_no} " +
            "WHERE soi_id = #{soi_id}")
    int updateSalesOrderItem(SalesOrderItem salesOrderItem);
    
    @Delete("DELETE FROM salesorderitem WHERE soi_id = #{soi_id}")
    int deleteSalesOrderItem(String soi_id);
    
    @Select("SELECT * FROM salesorderitem WHERE soi_id = #{soi_id}")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    SalesOrderItem selectSalesOrderItemById(String soi_id);
    
    @Select("SELECT * FROM salesorderitem ORDER BY soi_id")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    List<SalesOrderItem> selectAllSalesOrderItems();
    
    @Select("SELECT * FROM salesorderitem WHERE so_id = #{so_id}")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsBySoId(String so_id);
    
    @Select("SELECT * FROM salesorderitem WHERE drug_id = #{drug_id}")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsByDrugId(String drug_id);
    
    @Select("SELECT * FROM salesorderitem WHERE batch_no = #{batch_no}")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsByBatchNo(String batch_no);
    
    @Insert({"<script>",
            "INSERT INTO salesorderitem (soi_id, so_id, drug_id, quantity, price, batch_no) VALUES ",
            "<foreach collection='items' item='item' separator=','>",
            "(#{item.soi_id}, #{item.so_id}, #{item.drug_id}, #{item.quantity}, #{item.price}, #{item.batch_no})",
            "</foreach>",
            "</script>"})
    int batchInsertSalesOrderItems(@Param("items") List<SalesOrderItem> items);
    
    @Update("UPDATE salesorderitem SET price = #{subtotal} WHERE soi_id = #{soi_id}")
    int updateSubtotal(@Param("soi_id") String soi_id, @Param("subtotal") BigDecimal subtotal);
    
    @Delete("DELETE FROM salesorderitem WHERE so_id = #{so_id}")
    int deleteSalesOrderItemsBySoId(String so_id);
    @Select("SELECT * FROM salesorderitem ORDER BY soi_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "soi_id", column = "soi_id"),
        @Result(property = "so_id", column = "so_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "batch_no", column = "batch_no")
    })
    List<SalesOrderItem> selectSalesOrderItemsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM salesorderitem")
    long countSalesOrderItems();

}