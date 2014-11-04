package com.copex.example.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.copex.example.entity.Company;

public interface CompanyRepository extends
		PagingAndSortingRepository<Company, Long> {

}
