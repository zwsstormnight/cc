/*
 */
package com.copex.common.service.impl;

import javax.annotation.Resource;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.copex.common.entity.Log;
import com.copex.common.repositories.LogRepository;
import com.copex.common.service.LogService;
import com.copex.core.service.impl.BaseServiceImpl;

/**
 * Service - Log
 * 
 */
@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements
		LogService {

	@Resource(name = "logRepository")
	public void setReposity(PagingAndSortingRepository<Log, Long> repository) {
		super.setReposity(repository);
	}

	@Resource(name = "logRepository")
	private LogRepository repository;

	public void clear() {
		repository.deleteAll();
	}

}