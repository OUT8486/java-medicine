package com.example.spring_boot.dao;

import com.example.spring_boot.entity.WarehouseIn;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 入库单 Mapper，字段与 warehousein 表结构保持一致：
 * wi_id / po_id / warehouse_id / in_date / batch_no / validity_date。
 */
@Mapper
public interface WarehouseInMapper {

    @Insert("INSERT INTO warehousein (wi_id, po_id, warehouse_id, in_date, batch_no, validity_date) " +
            "VALUES (#{wi_id}, #{po_id}, #{warehouse_id}, #{in_date}, #{batch_no}, #{validity_date})")
    int insertWarehouseIn(WarehouseIn warehouseIn);

    @Update("UPDATE warehousein SET " +
            "po_id = #{po_id}, warehouse_id = #{warehouse_id}, " +
            "in_date = #{in_date}, batch_no = #{batch_no}, validity_date = #{validity_date} " +
            "WHERE wi_id = #{wi_id}")
    int updateWarehouseIn(WarehouseIn warehouseIn);

    @Delete("DELETE FROM warehousein WHERE wi_id = #{wi_id}")
    int deleteWarehouseIn(String wi_id);

    @Select("SELECT * FROM warehousein WHERE wi_id = #{wi_id}")
    @Results({
            @Result(property = "wi_id", column = "wi_id"),
            @Result(property = "po_id", column = "po_id"),
            @Result(property = "warehouse_id", column = "warehouse_id"),
            @Result(property = "in_date", column = "in_date"),
            @Result(property = "batch_no", column = "batch_no"),
            @Result(property = "validity_date", column = "validity_date")
    })
    WarehouseIn selectWarehouseInById(String wi_id);

    @Select("SELECT * FROM warehousein ORDER BY wi_id")
    @Results({
            @Result(property = "wi_id", column = "wi_id"),
            @Result(property = "po_id", column = "po_id"),
            @Result(property = "warehouse_id", column = "warehouse_id"),
            @Result(property = "in_date", column = "in_date"),
            @Result(property = "batch_no", column = "batch_no"),
            @Result(property = "validity_date", column = "validity_date")
    })
    List<WarehouseIn> selectAllWarehouseIns();

    @Select("SELECT * FROM warehousein WHERE po_id = #{po_id} ORDER BY wi_id")
    @Results({
            @Result(property = "wi_id", column = "wi_id"),
            @Result(property = "po_id", column = "po_id"),
            @Result(property = "warehouse_id", column = "warehouse_id"),
            @Result(property = "in_date", column = "in_date"),
            @Result(property = "batch_no", column = "batch_no"),
            @Result(property = "validity_date", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByPoId(String po_id);

    @Select("SELECT * FROM warehousein WHERE warehouse_id = #{warehouse_id}")
    @Results({
            @Result(property = "wi_id", column = "wi_id"),
            @Result(property = "po_id", column = "po_id"),
            @Result(property = "warehouse_id", column = "warehouse_id"),
            @Result(property = "in_date", column = "in_date"),
            @Result(property = "batch_no", column = "batch_no"),
            @Result(property = "validity_date", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByWarehouseId(String warehouse_id);

    @Select("SELECT * FROM warehousein WHERE batch_no = #{batch_no}")
    @Results({
            @Result(property = "wi_id", column = "wi_id"),
            @Result(property = "po_id", column = "po_id"),
            @Result(property = "warehouse_id", column = "warehouse_id"),
            @Result(property = "in_date", column = "in_date"),
            @Result(property = "batch_no", column = "batch_no"),
            @Result(property = "validity_date", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByBatchNo(String batch_no);
}
