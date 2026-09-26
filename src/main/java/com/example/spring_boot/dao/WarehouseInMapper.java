package com.example.spring_boot.dao;
import org.apache.ibatis.annotations.Param;

import com.example.spring_boot.entity.WarehouseIn;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
    WarehouseIn selectWarehouseInById(String wi_id);

    @Select("SELECT * FROM warehousein ORDER BY wi_id")
    List<WarehouseIn> selectAllWarehouseIns();


    @Select("SELECT * FROM warehousein ORDER BY wi_id LIMIT #{offset}, #{size}")
    List<WarehouseIn> selectWarehouseInsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM warehousein")
    long countWarehouseIns();

}
