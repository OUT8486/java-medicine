package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.spring_boot.entity.SalesOrderItem;


@Mapper
public interface SalesOrderItemMapper {
    
    @Insert("INSERT INTO salesorderitem (" +
            "soi_id, so_id, drug_id, quantity, price, batch_no) " +
            "VALUES (#{soiId}, #{soId}, #{drugId}, #{quantity}, #{price}, #{batchNo})")
    @Options(useGeneratedKeys = true, keyProperty = "soiId")
    int insertSalesOrderItem(SalesOrderItem salesOrderItem);
    

    @Delete("DELETE FROM salesorderitem WHERE so_id = #{soId}")
    int deleteSalesOrderItemsBySoId(String so_id);

}
