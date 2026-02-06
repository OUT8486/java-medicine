package com.example.spring_boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
            "VALUES (#{drug_id}, #{generic_name}, #{approval_no}, #{dosage_form}, " +
            "#{specification}, #{unit}, #{purchase_price}, #{retail_price}, #{manufacturer_id})")
    @Options(useGeneratedKeys = true, keyProperty = "drug_id")
    int insertDrug(Drug drug);
    
    @Update("UPDATE drug SET " +
            "generic_name = #{generic_name}, approval_no = #{approval_no}, " +
            "dosage_form = #{dosage_form}, specification = #{specification}, " +
            "unit = #{unit}, purchase_price = #{purchase_price}, " +
            "retail_price = #{retail_price}, manufacturer_id = #{manufacturer_id} " +
            "WHERE drug_id = #{drug_id}")
    int updateDrug(Drug drug);
    
    @Delete("DELETE FROM drug WHERE drug_id = #{drug_id}")
    int deleteDrug(String drug_id);
    
    @Select("SELECT * FROM drug WHERE drug_id = #{drug_id}")
    @Results({
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "generic_name", column = "generic_name"),
        @Result(property = "approval_no", column = "approval_no"),
        @Result(property = "dosage_form", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "retail_price", column = "retail_price"),
        @Result(property = "manufacturer_id", column = "manufacturer_id")
    })
    Drug selectDrugById(String drug_id);
    
    @Select("SELECT * FROM drug")
    @Results({
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "generic_name", column = "generic_name"),
        @Result(property = "approval_no", column = "approval_no"),
        @Result(property = "dosage_form", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "retail_price", column = "retail_price"),
        @Result(property = "manufacturer_id", column = "manufacturer_id")
    })
    List<Drug> selectAllDrugs();
    
    @Select("SELECT m.name AS manufacturer_name, d.* " +
            "FROM drug d " +
            "INNER JOIN manufacturer m ON d.manufacturer_id = m.manufacturer_id " +
            "WHERE d.drug_id = #{drug_id}")
    @Results({
        @Result(property = "drug_id", column = "drug_id"),
        @Result(property = "generic_name", column = "generic_name"),
        @Result(property = "approval_no", column = "approval_no"),
        @Result(property = "dosage_form", column = "dosage_form"),
        @Result(property = "specification", column = "specification"),
        @Result(property = "unit", column = "unit"),
        @Result(property = "purchase_price", column = "purchase_price"),
        @Result(property = "retail_price", column = "retail_price"),
        @Result(property = "manufacturer_id", column = "manufacturer_id"),
        @Result(property = "manufacturer_name", column = "manufacturer_name")
    })
    DrugWithManufacturer selectDrugWithManufacturer(String drug_id);
}