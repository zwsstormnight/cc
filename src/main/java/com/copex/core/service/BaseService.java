package com.copex.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BaseService<T, ID extends Serializable> {

	T find(ID id);

	List<T> findAll();

	List<T> findList(ID[] ids);

	List<T> findList(Sort sort);

	Page<T> findPage(Pageable pageable);
	
	long count();

	boolean exists(ID id);

	void save(T entity);

	T update(T entity);

	T update(T entity, String... ignoreProperties);

	void delete(ID id);

	void delete(ID[] ids);

	void delete(T entity);
}
