package com.example.spring_boot.service;

import com.example.spring_boot.dao.ManufacturerMapper;
import com.example.spring_boot.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerMapper manufacturerMapper;

    // 新增生产厂家
    public int addManufacturer(Manufacturer manufacturer) {
        return manufacturerMapper.insertManufacturer(manufacturer);
    }

    // 修改生产厂家信息
    public void updateManufacturer(Manufacturer manufacturer) {
        manufacturerMapper.updateManufacturer(manufacturer);
    }

    // 删除生产厂家
    public void deleteManufacturer(String manufacturerId) {
        manufacturerMapper.deleteManufacturer(manufacturerId);
    }

    // 根据ID查询生产厂家
    public Manufacturer getManufacturerById(String manufacturerId) {
        return manufacturerMapper.selectManufacturerById(manufacturerId);
    }

    // 查询所有生产厂家
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerMapper.selectAllManufacturers();
    }

    // 根据名称查询生产厂家
    public List<Manufacturer> getManufacturersByName(String name) {
        return manufacturerMapper.selectManufacturersByName(name);
    }
    
}