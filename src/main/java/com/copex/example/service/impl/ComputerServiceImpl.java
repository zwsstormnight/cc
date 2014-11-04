package com.copex.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.copex.common.service.StaticizeService;
import com.copex.core.service.impl.BaseServiceImpl;
import com.copex.example.entity.Computer;
import com.copex.example.repositories.ComputerRepository;
import com.copex.example.service.ComputerService;

@Service("computerServiceImpl")
public class ComputerServiceImpl extends BaseServiceImpl<Computer, Long>
		implements ComputerService {

	@Resource(name = "computerRepository")
	private ComputerRepository repository;

	@Resource(name = "staticizeServiceImpl")
	private StaticizeService staticService;

	@Resource(name = "computerRepository")
	public void setReposity(
			PagingAndSortingRepository<Computer, Long> repository) {
		super.setReposity(repository);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public Computer find(Long id) {
		return super.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public List<Computer> findAll() {
		return super.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public List<Computer> findList(Long[] ids) {
		return super.findList(ids);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public List<Computer> findList(Sort sort) {
		return super.findList(sort);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public Page<Computer> findPage(Pageable pageable) {
		return super.findPage(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("computer")
	public Page<Computer> findByNameLike(String name, Pageable pageable) {
		return repository.findByNameLikeIgnoreCase(name, pageable);
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public void save(Computer entity) {
		super.save(entity);
		staticService.build(entity);
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public Computer update(Computer entity) {
		Computer computer = super.find(entity.getId());
		entity.setCreateDate(computer.getCreateDate());
		super.update(entity);
		
		staticService.build(computer);

		return computer;
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public Computer update(Computer entity, String... ignoreProperties) {
		Computer computer = super.update(entity, ignoreProperties);
		staticService.build(computer);

		return computer;
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public void delete(Long id) {
		if (id != null) {
			staticService.delete(find(id));
		}
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public void delete(Long[] ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "computer", allEntries = true)
	public void delete(Computer entity) {
		super.delete(entity);
		if (entity != null) {
			staticService.delete(entity);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int buildAllStatic() {
		int buildCount = 0;

		int total = (int) Math.ceil((double) repository.count() / (double) 20);
		for (int i = 0; i < total; i++) {

			Page<Computer> computers = repository
					.findAll(new PageRequest(i, 20));
			for (Computer computer : computers) {
				buildCount += staticService.build(computer);
			}
		}
		return buildCount;
	}

}
