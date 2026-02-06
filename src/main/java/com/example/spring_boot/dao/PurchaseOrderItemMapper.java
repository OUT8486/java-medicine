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

import com.example.spring_boot.entity.PurchaseOrderItem;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PurchaseOrderItemMapper {
    
    @Insert("INSERT INTO purchaseorderitem (" +
            "poi_id, po_id, drug_id, quantity, price) " +
            "VALUES (#{poi_id}, #{po_id}, #{drug_id}, #{quantity}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "poi_id")
    int insertPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
    
    @Update("UPDATE purchaseorderitem SET " +
            "po_id = #{po_id}, drug_id = #{drug_id}, quantity = #{quantity}, price = #{price} " +
            "WHERE poi_id = #{poi_id}")
    int updatePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
    
    @Delete("DELETE FROM purchaseorderitem WHERE poi_id = #{poi_id}")
    int deletePurchaseOrderItem(String poi_id);
    
    @Select("SELECT * FROM purchaseorderitem WHERE poi_id = #{poi_id}")
    @Results({
        @Result(property = "poi_id", column = "poi_id"),
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price")
    })
    PurchaseOrderItem selectPurchaseOrderItemById(String poi_id);
    
    @Select("SELECT * FROM purchaseorderitem ORDER BY poi_id")
    @Results({
        @Result(property = "poi_id", column = "poi_id"),
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price")
    })
    List<PurchaseOrderItem> selectAllPurchaseOrderItems();
    
    @Select("SELECT * FROM purchaseorderitem WHERE po_id = #{po_id} ORDER BY poi_id")
    @Results({
        @Result(property = "poi_id", column = "poi_id"),
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price")
    })
    List<PurchaseOrderItem> selectPurchaseOrderItemsByPoId(String po_id);
    
    @Select("SELECT * FROM purchaseorderitem WHERE drug_id = #{drug_id} ORDER BY poi_id")
    @Results({
        @Result(property = "poi_id", column = "poi_id"),
        @Result(property = "po_id", column = "po_id"),
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price")
    })
    List<PurchaseOrderItem> selectPurchaseOrderItemsByDrugId(String drug_id);
    
    @Update("UPDATE purchaseorderitem SET price = #{subtotal} WHERE poi_id = #{poi_id}")
    int updateSubtotal(@Param("poi_id") String poi_id, @Param("subtotal") BigDecimal subtotal);
    
    @Insert({"<script>",
            "INSERT INTO purchaseorderitem (poi_id, po_id, drug_id, quantity, price) VALUES ",
            "<foreach collection='items' item='item' separator=','>",
            "(#{item.poi_id}, #{item.po_id}, #{item.drug_id}, #{item.quantity}, #{item.price})",
            "</foreach>",
            "</script>"})
    int batchInsertPurchaseOrderItems(@Param("items") List<PurchaseOrderItem> items);
    
    @Delete("DELETE FROM purchaseorderitem WHERE po_id = #{po_id}")
    int deletePurchaseOrderItemsByPoId(String po_id);
}