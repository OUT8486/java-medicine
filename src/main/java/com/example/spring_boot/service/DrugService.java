package com.example.spring_boot.service;

import com.example.spring_boot.dao.DrugMapper;
import com.example.spring_boot.entity.Drug;
import com.example.spring_boot.entity.PageResult;
import com.example.spring_boot.utils.IdGenerator;
import com.example.spring_boot.utils.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DrugService {
    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String DRUG_CACHE_KEY = "drug:";
    private static final String DRUG_LIST_CACHE_KEY = "drug:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增药品
    public int addDrug(Drug drug) {
        if (drug.getDrugId() == null || drug.getDrugId().isBlank()) {
            drug.setDrugId(IdGenerator.next("DR"));
        }
        int result = drugMapper.insertDrug(drug);
        if (result > 0) {
            redisUtils.delete(DRUG_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改药品
    public void updateDrug(Drug drug) {
        drugMapper.updateDrug(drug);
        redisUtils.evict(DRUG_CACHE_KEY + drug.getDrugId(), DRUG_LIST_CACHE_KEY);
    }

    // 删除药品
    public void deleteDrug(String drugId) {
        drugMapper.deleteDrug(drugId);
        redisUtils.evict(DRUG_CACHE_KEY + drugId, DRUG_LIST_CACHE_KEY);
    }

    // 根据ID查询
    public Drug getDrugById(String drugId) {
        return redisUtils.getOrLoad(DRUG_CACHE_KEY + drugId, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                () -> drugMapper.selectDrugById(drugId));
    }

    // 查询所有药品
    public List<Drug> getAllDrugs() {
        return redisUtils.getListOrLoad(DRUG_LIST_CACHE_KEY, CACHE_EXPIRE_TIME, TimeUnit.MINUTES,
                drugMapper::selectAllDrugs);
    }


    // 分页查询
    public PageResult<Drug> getDrugsPage(int page, int size) {
        int p = Math.max(page, 1);
        int s = Math.min(Math.max(size, 1), 100);
        int offset = (p - 1) * s;
        List<Drug> list = drugMapper.selectDrugsPage(offset, s);
        long total = drugMapper.countDrugs();
        return new PageResult<>(list, total, p, s);
    }
}
