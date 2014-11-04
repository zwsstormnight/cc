/*
 */
package com.copex.core.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;

import com.copex.core.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * Template Directive
 * 
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

	private static final String ID_PARAMETER_NAME = "id";
	private static final String COUNT_PARAMETER_NAME = "count";
	private static final String ORDER_BY_PARAMETER_NAME = "orderBy";
	private static final String ORDER_BY_ITEM_SEPARATOR = "\\s*,\\s*";
	private static final String ORDER_BY_FIELD_SEPARATOR = "\\s+";

	/**
	 * Get ID
	 * 
	 * @param params
	 * @return ID
	 */
	protected Long getId(Map<String, TemplateModel> params)
			throws TemplateModelException {
		return FreemarkerUtils.getParameter(ID_PARAMETER_NAME, Long.class,
				params);
	}

	/**
	 * Get Count
	 * 
	 * @param params
	 * @return count
	 */
	protected Integer getCount(Map<String, TemplateModel> params)
			throws TemplateModelException {
		return FreemarkerUtils.getParameter(COUNT_PARAMETER_NAME,
				Integer.class, params);
	}

	protected Sort getSort(Map<String, TemplateModel> params,
			String... ignoreProperties) throws TemplateModelException {
		String orderBy = StringUtils.trim(FreemarkerUtils.getParameter(
				ORDER_BY_PARAMETER_NAME, String.class, params));
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		if (StringUtils.isNotEmpty(orderBy)) {
			String[] orderByItems = orderBy.split(ORDER_BY_ITEM_SEPARATOR);
			for (String orderByItem : orderByItems) {
				if (StringUtils.isNotEmpty(orderByItem)) {
					String property = null;
					Sort.Direction direction = null;
					String[] orderBys = orderByItem
							.split(ORDER_BY_FIELD_SEPARATOR);
					if (orderBys.length == 1) {
						property = orderBys[0];
					} else if (orderBys.length >= 2) {
						property = orderBys[0];
						try {
							direction = Sort.Direction.fromString(orderBys[1]);
						} catch (IllegalArgumentException e) {
							continue;
						}
					} else {
						continue;
					}
					if (!ArrayUtils.contains(ignoreProperties, property)) {
						orders.add(new Sort.Order(direction, property)
								.ignoreCase());
					}
				}
			}
		}

		return new Sort(orders);
	}

	/**
	 * Set Local Variable
	 * 
	 * @param name
	 * @param value
	 * @param env
	 * @param body
	 */
	protected void setLocalVariable(String name, Object value, Environment env,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateModel sourceVariable = FreemarkerUtils.getVariable(name, env);
		FreemarkerUtils.setVariable(name, value, env);
		body.render(env.getOut());
		FreemarkerUtils.setVariable(name, sourceVariable, env);
	}

	/**
	 * Set Local Variable
	 * 
	 * @param variables
	 * @param env
	 * @param body
	 */
	protected void setLocalVariables(Map<String, Object> variables,
			Environment env, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Map<String, Object> sourceVariables = new HashMap<String, Object>();
		for (String name : variables.keySet()) {
			TemplateModel sourceVariable = FreemarkerUtils.getVariable(name,
					env);
			sourceVariables.put(name, sourceVariable);
		}
		FreemarkerUtils.setVariables(variables, env);
		body.render(env.getOut());
		FreemarkerUtils.setVariables(sourceVariables, env);
	}

}