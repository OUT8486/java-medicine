package com.example.spring_boot.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.spring_boot.entity.Manufacturer;

import java.util.List;

@Mapper
public interface ManufacturerMapper {
    // 新增生产厂家
    int insertManufacturer(Manufacturer manufacturer);
    
    // 修改生产厂家信息
    void updateManufacturer(Manufacturer manufacturer);
    
    // 删除生产厂家
    void deleteManufacturer(String manufacturer_id);
    
    // 根据ID查询生产厂家
    Manufacturer selectManufacturerById(String manufacturer_id);
    
    // 查询所有生产厂家
    List<Manufacturer> selectAllManufacturers();
    
    // 根据名称查询生产厂家
    List<Manufacturer> selectManufacturersByName(String name);
    
    // 根据统一社会信用代码查询生产厂家
    Manufacturer selectManufacturerByCreditCode(String credit_code);
    
}