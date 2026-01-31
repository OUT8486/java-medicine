package com.example.spring_boot.service;

import com.example.spring_boot.dao.DrugMapper;
import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.DrugWithManufacturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugService {
    @Autowired
    private DrugMapper drugMapper;

    // 新增药品
    public int addDrug(Drug drug) {
        return drugMapper.insertDrug(drug);
    }

    // 修改药品
    public void updateDrug(Drug drug) {
        drugMapper.updateDrug(drug);
    }

    // 删除药品
    public void deleteDrug(String drugId) {
        drugMapper.deleteDrug(drugId);
    }

    // 根据ID查询
    public Drug getDrugById(String drugId) {
        return drugMapper.selectDrugById(drugId);
    }

    // 查询所有药�?
    public List<Drug> getAllDrugs() {
        return drugMapper.selectAllDrugs();
    }

    // 联表查询（可选）
    public DrugWithManufacturer getDrugWithManufacturer(String drugId) {
        return drugMapper.selectDrugWithManufacturer(drugId);
    }
}
