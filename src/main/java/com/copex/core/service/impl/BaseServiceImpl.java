package com.copex.core.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.copex.core.entity.BaseEntity;
import com.copex.core.service.BaseService;

/**
 * Service - Implementation
 * 
 */
@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements
		BaseService<T, ID> {

	private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] {
			BaseEntity.ID_PROPERTY_NAME, BaseEntity.CREATE_DATE_PROPERTY_NAME,
			BaseEntity.MODIFY_DATE_PROPERTY_NAME };

	@PersistenceContext
	protected EntityManager entityManager;

	private PagingAndSortingRepository<T, ID> repository;

	public void setReposity(PagingAndSortingRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public T find(ID id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return IteratorUtils.toList(repository.findAll().iterator());
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(ID[] ids) {
		Iterable<ID> iterable = Arrays.asList(ids);
		return IteratorUtils.toList(repository.findAll(iterable).iterator());
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Sort sort) {
		return IteratorUtils.toList(repository.findAll(sort).iterator());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long count() {
		return repository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return repository.exists(id);
	}

	@Override
	@Transactional
	public void save(T entity) {
		repository.save(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public T update(T entity, String... ignoreProperties) {
		Assert.notNull(entity);
		if (isManaged(entity)) {
			throw new IllegalArgumentException("Entity must not be managed");
		}
		T persistant = find(getIdentifier(entity));
		if (persistant != null) {
			copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(
					ignoreProperties, UPDATE_IGNORE_PROPERTIES));
			return update(persistant);
		} else {
			return update(entity);
		}
	}

	@Override
	@Transactional
	public void delete(ID id) {
		repository.delete(id);
	}

	@Override
	@Transactional
	public void delete(ID[] ids) {
		if (ids != null) {
			for (ID id : ids) {
				repository.delete(id);
			}
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		repository.delete(entity);
	}

	private boolean isManaged(T entity) {
		return entityManager.contains(entity);
	}

	@SuppressWarnings("unchecked")
	public ID getIdentifier(T entity) {
		Assert.notNull(entity);
		return (ID) entityManager.getEntityManagerFactory()
				.getPersistenceUnitUtil().getIdentifier(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void copyProperties(Object source, Object target,
			String[] ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		PropertyDescriptor[] targetPds = BeanUtils
				.getPropertyDescriptors(target.getClass());
		List<String> ignoreList = (ignoreProperties != null) ? Arrays
				.asList(ignoreProperties) : null;
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null
					&& (ignoreProperties == null || (!ignoreList
							.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object sourceValue = readMethod.invoke(source);
						Object targetValue = readMethod.invoke(target);
						if (sourceValue != null && targetValue != null
								&& targetValue instanceof Collection) {
							Collection collection = (Collection) targetValue;
							collection.clear();
							collection.addAll((Collection) sourceValue);
						} else {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, sourceValue);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}
}
