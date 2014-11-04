/*
 */
package com.copex.common.service;

import java.util.List;

import com.copex.common.LogConfig;

/**
 * Service - Log Configuration
 * 
 */
public interface LogConfigService {

	/**
	 * Get all log's configuration
	 * 
	 * @return 
	 */
	List<LogConfig> getAll();

}