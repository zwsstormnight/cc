package com.copex.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.copex.core.service.impl.BaseServiceImpl;
import com.copex.example.entity.Company;
import com.copex.example.service.CompanyService;

@Service("companyServiceImpl")
public class CompanyServiceImpl extends BaseServiceImpl<Company, Long>
		implements CompanyService {
	
	@Resource(name = "companyRepository")
	public void setReposity(PagingAndSortingRepository<Company, Long> repository) {
		super.setReposity(repository);
	}

	@Override
	@Cacheable("company")
	public Company find(Long id) {
		return super.find(id);
	}

	@Override
	@Cacheable("company")
	public List<Company> findAll() {

		return super.findAll();
	}

	@Override
	@Cacheable("company")
	public List<Company> findList(Long[] ids) {

		return super.findList(ids);
	}

	@Override
	@Cacheable("company")
	public List<Company> findList(Sort sort) {
		return super.findList(sort);
	}

	@Override
	@Cacheable("company")
	public Page<Company> findPage(Pageable pageable) {
		return super.findPage(pageable);
	}

	@Override
	@CacheEvict(value = { "computer", "company" }, allEntries = true)
	public Company update(Company entity) {
		return super.update(entity);
	}

	@Override
	@CacheEvict(value = { "computer", "company" }, allEntries = true)
	public Company update(Company entity, String... ignoreProperties) {
		return super.update(entity, ignoreProperties);
	}

	@Override
	@CacheEvict(value = { "computer", "company" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = { "computer", "company" }, allEntries = true)
	public void delete(Long[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = { "computer", "company" }, allEntries = true)
	public void delete(Company entity) {
		super.delete(entity);
	}


}
