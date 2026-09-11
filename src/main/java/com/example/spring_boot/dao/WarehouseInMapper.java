package com.example.spring_boot.dao;
import org.apache.ibatis.annotations.Param;

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
            "VALUES (#{wiId}, #{poId}, #{warehouseId}, #{inDate}, #{batchNo}, #{validityDate})")
    int insertWarehouseIn(WarehouseIn warehouseIn);

    @Update("UPDATE warehousein SET " +
            "po_id = #{poId}, warehouse_id = #{warehouseId}, " +
            "in_date = #{inDate}, batch_no = #{batchNo}, validity_date = #{validityDate} " +
            "WHERE wi_id = #{wiId}")
    int updateWarehouseIn(WarehouseIn warehouseIn);

    @Delete("DELETE FROM warehousein WHERE wi_id = #{wiId}")
    int deleteWarehouseIn(String wi_id);

    @Select("SELECT * FROM warehousein WHERE wi_id = #{wiId}")
    @Results({
            @Result(property = "wiId", column = "wi_id"),
            @Result(property = "poId", column = "po_id"),
            @Result(property = "warehouseId", column = "warehouse_id"),
            @Result(property = "inDate", column = "in_date"),
            @Result(property = "batchNo", column = "batch_no"),
            @Result(property = "validityDate", column = "validity_date")
    })
    WarehouseIn selectWarehouseInById(String wi_id);

    @Select("SELECT * FROM warehousein ORDER BY wi_id")
    @Results({
            @Result(property = "wiId", column = "wi_id"),
            @Result(property = "poId", column = "po_id"),
            @Result(property = "warehouseId", column = "warehouse_id"),
            @Result(property = "inDate", column = "in_date"),
            @Result(property = "batchNo", column = "batch_no"),
            @Result(property = "validityDate", column = "validity_date")
    })
    List<WarehouseIn> selectAllWarehouseIns();

    @Select("SELECT * FROM warehousein WHERE po_id = #{poId} ORDER BY wi_id")
    @Results({
            @Result(property = "wiId", column = "wi_id"),
            @Result(property = "poId", column = "po_id"),
            @Result(property = "warehouseId", column = "warehouse_id"),
            @Result(property = "inDate", column = "in_date"),
            @Result(property = "batchNo", column = "batch_no"),
            @Result(property = "validityDate", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByPoId(String po_id);

    @Select("SELECT * FROM warehousein WHERE warehouse_id = #{warehouseId}")
    @Results({
            @Result(property = "wiId", column = "wi_id"),
            @Result(property = "poId", column = "po_id"),
            @Result(property = "warehouseId", column = "warehouse_id"),
            @Result(property = "inDate", column = "in_date"),
            @Result(property = "batchNo", column = "batch_no"),
            @Result(property = "validityDate", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByWarehouseId(String warehouse_id);

    @Select("SELECT * FROM warehousein WHERE batch_no = #{batchNo}")
    @Results({
            @Result(property = "wiId", column = "wi_id"),
            @Result(property = "poId", column = "po_id"),
            @Result(property = "warehouseId", column = "warehouse_id"),
            @Result(property = "inDate", column = "in_date"),
            @Result(property = "batchNo", column = "batch_no"),
            @Result(property = "validityDate", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsByBatchNo(String batch_no);
    @Select("SELECT * FROM warehousein ORDER BY wi_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "wiId", column = "wi_id"),
        @Result(property = "poId", column = "po_id"),
        @Result(property = "warehouseId", column = "warehouse_id"),
        @Result(property = "inDate", column = "in_date"),
        @Result(property = "batchNo", column = "batch_no"),
        @Result(property = "validityDate", column = "validity_date")
    })
    List<WarehouseIn> selectWarehouseInsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM warehousein")
    long countWarehouseIns();

}
