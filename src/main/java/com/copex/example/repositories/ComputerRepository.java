package com.copex.example.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.copex.example.entity.Computer;

public interface ComputerRepository extends
		PagingAndSortingRepository<Computer, Long> {
	
	Page<Computer> findByNameLikeIgnoreCase(String name, Pageable pageable);

}
