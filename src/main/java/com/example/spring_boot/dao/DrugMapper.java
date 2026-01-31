package com.example.spring_boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.DrugWithManufacturer;

@Mapper
public interface DrugMapper {
    int insertDrug(Drug drug);
    void updateDrug(Drug drug);
    void deleteDrug(String drug_id);
    Drug selectDrugById(String drug_id);
    List<Drug> selectAllDrugs();
    DrugWithManufacturer selectDrugWithManufacturer(String drug_id);
}
