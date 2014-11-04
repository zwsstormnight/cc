/*
 */
package com.copex.common.service;

import com.copex.common.entity.Log;
import com.copex.core.service.BaseService;

/**
 * Service - Log
 * 
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * Clear all logs
	 */
	void clear();

}