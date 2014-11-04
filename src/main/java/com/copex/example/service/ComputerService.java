package com.copex.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.copex.core.service.BaseService;
import com.copex.example.entity.Computer;

public interface ComputerService extends BaseService<Computer, Long> {

	Page<Computer> findByNameLike(String name, Pageable pageable);
	
	/**
	 * Build All
	 * 
	 * @return build count
	 */
	int buildAllStatic();
}
