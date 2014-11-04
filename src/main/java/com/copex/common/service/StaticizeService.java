/*
 */
package com.copex.common.service;

import java.util.Map;

import com.copex.core.Staticizable;

/**
 * Service - Static
 * 
 */
public interface StaticizeService {

	/**
	 * Build Static
	 * 
	 * @return build count
	 */
	int build(Staticizable entity);

	/**
	 * Delete static
	 * 
	 * @return delete count
	 */
	int delete(Staticizable entity);

	/**
	 * Build static resource
	 * 
	 * @param templatePath
	 * @param staticPath
	 * @param model
	 * @return build count
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * Build static resource
	 * 
	 * @param templatePath
	 * @param staticPath
	 * @return build count
	 */
	int build(String templatePath, String staticPath);

	/**
	 * Build index
	 * 
	 * @return build count
	 */
	int buildIndex();

	/**
	 * Build other
	 * 
	 * @return build count
	 */
	int buildOther();

	/**
	 * Build All
	 * 
	 * @return build count
	 */
	int buildAll();

	/**
	 * delete static resource
	 * 
	 * @param staticPath
	 * @return delete count
	 */
	int delete(String staticPath);

	/**
	 * delete index
	 * 
	 * @return delete count
	 */
	int deleteIndex();

	/**
	 * delete other
	 * 
	 * @return delete count
	 */
	int deleteOther();
}