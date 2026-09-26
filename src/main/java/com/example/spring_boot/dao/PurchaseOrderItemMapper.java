package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.spring_boot.entity.PurchaseOrderItem;


@Mapper
public interface PurchaseOrderItemMapper {
    
    @Insert("INSERT INTO purchaseorderitem (" +
            "poi_id, po_id, drug_id, quantity, price) " +
            "VALUES (#{poiId}, #{poId}, #{drugId}, #{quantity}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "poiId")
    int insertPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem);
    

    @Delete("DELETE FROM purchaseorderitem WHERE po_id = #{poId}")
    int deletePurchaseOrderItemsByPoId(String po_id);

}
