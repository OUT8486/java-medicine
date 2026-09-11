package com.example.spring_boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.DrugWithManufacturer;

@Mapper
public interface DrugMapper {
    
    @Insert("INSERT INTO drug (" +
            "drug_id, generic_name, approval_no, dosage_form, specification, " +
            "unit, purchase_price, retail_price, manufacturer_id) " +
            "VALUES (#{drugId}, #{genericName}, #{approvalNo}, #{dosageForm}, " +
            "#{specification}, #{unit}, #{purchasePrice}, #{retailPrice}, #{manufacturerId})")
    @Options(useGeneratedKeys = true, keyProperty = "drugId")
    int insertDrug(Drug drug);
    
    @Update("UPDATE drug SET " +
            "generic_name = #{genericName}, approval_no = #{approvalNo}, " +
            "dosage_form = #{dosageForm}, specification = #{specification}, " +
            "unit = #{unit}, purchase_price = #{purchasePrice}, " +
            "retail_price = #{retailPrice}, manufacturer_id = #{manufacturerId} " +
            "WHERE drug_id = #{drugId}")
    int updateDrug(Drug drug);
    
    @Delete("DELETE FROM drug WHERE drug_id = #{drugId}")
    int deleteDrug(String drug_id);
    
    @Select("SELECT * FROM drug WHERE drug_id = #{drugId}")
    @Results({
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "genericName", column = "generic_name"),
        @Result(property = "approvalNo", column = "approval_no"),
        @Result(property = "dosageForm", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchasePrice", column = "purchase_price"),
        @Result(property = "retailPrice", column = "retail_price"),
        @Result(property = "manufacturerId", column = "manufacturer_id")
    })
    Drug selectDrugById(String drug_id);
    
    @Select("SELECT * FROM drug")
    @Results({
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "genericName", column = "generic_name"),
        @Result(property = "approvalNo", column = "approval_no"),
        @Result(property = "dosageForm", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchasePrice", column = "purchase_price"),
        @Result(property = "retailPrice", column = "retail_price"),
        @Result(property = "manufacturerId", column = "manufacturer_id")
    })
    List<Drug> selectAllDrugs();
    
    @Select("SELECT m.name AS manufacturer_name, d.* " +
            "FROM drug d " +
            "INNER JOIN manufacturer m ON d.manufacturer_id = m.manufacturer_id " +
            "WHERE d.drug_id = #{drugId}")
    @Results({
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "genericName", column = "generic_name"),
        @Result(property = "approvalNo", column = "approval_no"),
        @Result(property = "dosageForm", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchasePrice", column = "purchase_price"),
        @Result(property = "retailPrice", column = "retail_price"),
        @Result(property = "manufacturerId", column = "manufacturer_id"),
        @Result(property = "manufacturerName", column = "manufacturer_name")
    })
    DrugWithManufacturer selectDrugWithManufacturer(String drug_id);

    @Select("SELECT * FROM drug ORDER BY drug_id LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "drugId", column = "drug_id"),
        @Result(property = "genericName", column = "generic_name"),
        @Result(property = "approvalNo", column = "approval_no"),
        @Result(property = "dosageForm", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchasePrice", column = "purchase_price"),
        @Result(property = "retailPrice", column = "retail_price"),
        @Result(property = "manufacturerId", column = "manufacturer_id")
    })
    List<Drug> selectDrugsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM drug")
    long countDrugs();
}