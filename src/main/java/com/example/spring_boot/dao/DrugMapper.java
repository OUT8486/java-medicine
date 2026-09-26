package com.example.spring_boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.spring_boot.entity.Drug;

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
    Drug selectDrugById(String drug_id);
    
    @Select("SELECT * FROM drug")
    List<Drug> selectAllDrugs();
    

    @Select("SELECT * FROM drug ORDER BY drug_id LIMIT #{offset}, #{size}")
    List<Drug> selectDrugsPage(@Param("offset") int offset, @Param("size") int size);

    @Select("SELECT COUNT(*) FROM drug")
    long countDrugs();
}