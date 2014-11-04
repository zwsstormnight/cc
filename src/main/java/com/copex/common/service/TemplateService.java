/*
 */
package com.copex.common.service;

import java.util.List;

import com.copex.core.Template;
import com.copex.core.Template.Type;

/**
 * Service - Template
 * 
 */
public interface TemplateService {

	/**
	 * Get all templates
	 *
	 * @return 
	 */
	List<Template> getAll();

	/**
	 * Get template
	 * 
	 * @param type
	 * @return 
	 */
	List<Template> getList(Type type);

	/**
	 * Get template
	 * 
	 * @param id
	 * @return 
	 */
	Template get(String id);

	/**
	 * Read template file content
	 * 
	 * @param id
	 * @return
	 */
	String read(String id);

	/**
	 * Read template file content
	 * 
	 * @param template
	 * @return
	 */
	String read(Template template);

	/**
	 * Write content to template
	 * 
	 * @param id
	 * @param content
	 */
	void write(String id, String content);

	/**
	 * Write content to template
	 * 
	 * @param template
	 * @param content
	 */
	void write(Template template, String content);

}