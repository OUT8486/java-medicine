package com.example.spring_boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring_boot.mapper.DrugMapper;
import com.example.spring_boot.pojo.Drug;

@SpringBootTest
public class ApplicationTests {

	@Autowired
	private DrugMapper drugMapper;

	@Test
	void test() {
		Drug drug = drugMapper.selectDrugById("DR1");
		System.out.println(drug);
	}

}
