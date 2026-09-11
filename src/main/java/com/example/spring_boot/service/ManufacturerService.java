package com.example.spring_boot.service;

import com.example.spring_boot.dao.ManufacturerMapper;
import com.example.spring_boot.entity.Manufacturer;
import com.example.spring_boot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerMapper manufacturerMapper;

    @Autowired
    private RedisUtils redisUtils;

    private static final String MANUFACTURER_CACHE_KEY = "manufacturer:";
    private static final String MANUFACTURER_LIST_CACHE_KEY = "manufacturer:list";
    private static final long CACHE_EXPIRE_TIME = 30;

    // 新增生产厂家
    public int addManufacturer(Manufacturer manufacturer) {
        int result = manufacturerMapper.insertManufacturer(manufacturer);
        if (result > 0) {
            redisUtils.delete(MANUFACTURER_LIST_CACHE_KEY);
        }
        return result;
    }

    // 修改生产厂家信息
    public void updateManufacturer(Manufacturer manufacturer) {
        manufacturerMapper.updateManufacturer(manufacturer);
        redisUtils.delete(MANUFACTURER_CACHE_KEY + manufacturer.getManufacturerId());
        redisUtils.delete(MANUFACTURER_LIST_CACHE_KEY);
    }

    // 删除生产厂家
    public void deleteManufacturer(String manufacturerId) {
        manufacturerMapper.deleteManufacturer(manufacturerId);
        redisUtils.delete(MANUFACTURER_CACHE_KEY + manufacturerId);
        redisUtils.delete(MANUFACTURER_LIST_CACHE_KEY);
    }

    // 根据ID查询生产厂家
    public Manufacturer getManufacturerById(String manufacturerId) {
        String cacheKey = MANUFACTURER_CACHE_KEY + manufacturerId;
        Manufacturer manufacturer = (Manufacturer) redisUtils.get(cacheKey);
        
        if (manufacturer != null) {
            return manufacturer;
        }
        
        manufacturer = manufacturerMapper.selectManufacturerById(manufacturerId);
        if (manufacturer != null) {
            redisUtils.set(cacheKey, manufacturer, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return manufacturer;
    }

    // 查询所有生产厂家
    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = (List<Manufacturer>) redisUtils.get(MANUFACTURER_LIST_CACHE_KEY);
        
        if (manufacturers != null) {
            return manufacturers;
        }
        
        manufacturers = manufacturerMapper.selectAllManufacturers();
        if (manufacturers != null && !manufacturers.isEmpty()) {
            redisUtils.set(MANUFACTURER_LIST_CACHE_KEY, manufacturers, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return manufacturers;
    }

    // 根据名称查询生产厂家
    public List<Manufacturer> getManufacturersByName(String name) {
        return manufacturerMapper.selectManufacturersByName(name);
    }
    
}